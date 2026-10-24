package tmg.flashback.composeApp.presentation.paddock

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase

class PaddockViewModel(
    private val isRssEnabledUseCase: IsRssEnabledUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<PaddockUiState> = MutableStateFlow(PaddockUiState(
        isRssEnabled = isRssEnabledUseCase.invoke()
    ))
    val uiState: StateFlow<PaddockUiState> = _uiState
}