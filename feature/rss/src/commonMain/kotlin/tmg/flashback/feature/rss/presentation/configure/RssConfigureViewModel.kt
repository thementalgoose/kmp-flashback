package tmg.flashback.feature.rss.presentation.configure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.RssRepository

class RssConfigureViewModel(
    private val rssRepository: RssRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<RssConfigureUiState> = MutableStateFlow(
        RssConfigureUiState(
            sources = getSources(),
            showDescription = rssRepository.rssShowDescription
        ))
    val uiState: StateFlow<RssConfigureUiState> = _uiState

    private fun getSources(): List<ConfiguredSupportedSource> {
        val enabledUrls = rssRepository.rssUrls
        return rssRepository.supportedSources.map {
            ConfiguredSupportedSource(article = it, isEnabled = enabledUrls.contains(it.rssLink))
        }
    }

    fun updateSource(source: SupportedSource, include: Boolean) {
        when (include) {
            true -> rssRepository.rssUrls += source.rssLink
            false -> rssRepository.rssUrls -= source.rssLink
        }
        _uiState.update { it.copy(sources = getSources()) }
    }

    fun updateShowDescription(enabled: Boolean) {
        rssRepository.rssShowDescription = enabled
        _uiState.update { it.copy(showDescription = enabled) }
    }
}