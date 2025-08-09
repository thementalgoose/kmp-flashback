package tmg.flashback.feature.highlights.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.highlights.domain.GetNewsItemsUseCase
import tmg.flashback.feature.highlights.repositories.HighlightsRepository
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class HighlightsViewModel(
    private val getNewsItemsUseCase: GetNewsItemsUseCase,
    private val highlightsRepository: HighlightsRepository,
    private val openWebpageUseCase: OpenWebpageUseCase,
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
                val news = getNewsItemsUseCase.getNews() ?: emptyList()
                _uiState.update {
                    it.copy(news = news.sortedByDescending { it.date })
                }
            }
        }
    }

    fun hide() {
        _uiState.update { it.copy(show = false) }
    }

    fun clickItem(url: String) {
        openWebpageUseCase(url)
    }
}