package tmg.flashback.feature.rss.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.format
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.feature.rss.presentation.feed.RssFeedUiState
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.feature.rss.usecases.GetRssArticleUseCase
import tmg.flashback.feature.rss.usecases.Response
import tmg.flashback.infrastructure.datetime.TimeManager
import tmg.flashback.infrastructure.datetime.timeFormatHHmmss
import tmg.flashback.network.rss.api.RssApi
import tmg.flashback.webbrowser.repository.WebRepository
import tmg.flashback.webbrowser.usecases.IsInAppBrowserEnabledUseCase
import kotlin.Boolean
import kotlin.String

class RSSFeedViewModel(
    private val rssRepository: RssRepository,
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val getRssArticlesUseCase: GetRssArticleUseCase,
    private val isInAppBrowserEnabledUseCase: IsInAppBrowserEnabledUseCase,
    private val webRepository: WebRepository,
    private val timeManager: TimeManager
): ViewModel() {

    private val _uiState: MutableStateFlow<RssFeedUiState> = MutableStateFlow(RssFeedUiState.Data(
        lastUpdated = null,
        isLoading = false,
        rssItems = emptyList(),
        hasSources = rssRepository.rssUrls.isNotEmpty(),
    ))
    val uiState: StateFlow<RssFeedUiState> = _uiState

    init {
        if (rssRepository.rssUrls.isNotEmpty()) {
            refresh()
        }
    }

    val inAppBrowserEnabled: Boolean
        get() = isInAppBrowserEnabledUseCase() && webRepository.enabled

    fun refresh() {
        viewModelScope.launch {
            _uiState.updateData { it.copy(isLoading = true) }
            val response = getRssArticlesUseCase()
            when (val resp = response) {
                is Response.Error -> {
                    _uiState.update { RssFeedUiState.NoNetwork }
                }
                is Response.Success -> {
                    _uiState.updateData {
                        it.copy(
                            lastUpdated = timeManager.now.time.format(timeFormatHHmmss),
                            isLoading = false,
                            hasSources = rssRepository.rssUrls.isNotEmpty(),
                            rssItems = resp.data
                                .distinctBy { it.link }
                                .sortedByDescending { it.date }
                        )
                    }
                }
            }
        }
    }

    fun openArticle(article: Article): Boolean {
        openWebpageUseCase(
            url = article.link,
            title = article.title
        )
        return true
    }

    private fun MutableStateFlow<RssFeedUiState>.updateData(updateCallback: (RssFeedUiState.Data) -> RssFeedUiState.Data) {
        this.update {
            if (it is RssFeedUiState.Data) {
                return@update updateCallback(it)
            } else {
                return@update updateCallback(RssFeedUiState.Data(isLoading = false))
            }
        }
    }
}
