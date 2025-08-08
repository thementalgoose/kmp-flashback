package tmg.flashback.highlights.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import tmg.flashback.highlights.repositories.HighlightsRepository
import tmg.flashback.infrastructure.datetime.dateFormatYYYYMMDD
import tmg.flashback.news.api.FlashbackNewsApi
import tmg.flashback.news.models.news.News
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class HighlightsViewModel(
    private val flashbackNewsApi: FlashbackNewsApi,
    private val highlightsRepository: HighlightsRepository,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {
    private val _uiState: MutableStateFlow<HighlightsUiState> = MutableStateFlow(HighlightsUiState(
        news = null,
        show = highlightsRepository.show
    ))
    val uiState: StateFlow<HighlightsUiState> = _uiState

    fun refresh() {
        if (highlightsRepository.show) {
            viewModelScope.launch(coroutineContext) {
                val news = flashbackNewsApi.getOverview()
                val items = news?.data?.mapNotNull { it.toPresentation() }
                _uiState.update {
                    it.copy(news = items)
                }
            }
        }
    }

    fun hide() {
        _uiState.update { it.copy(show = false) }
    }

    private fun News.toPresentation(): HighlightNewsItem? {
        return try {
            HighlightNewsItem(
                message = this.message,
                link = this.url,
                image = this.image,
                date = LocalDate.parse(this.dateAdded, dateFormatYYYYMMDD)
            )
        } catch (e: RuntimeException) {
            null
        }
    }
}