package tmg.flashback.feature.paddock.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaddockViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<PaddockUiState> = MutableStateFlow(PaddockUiState())
    val uiState: StateFlow<PaddockUiState> = _uiState
}