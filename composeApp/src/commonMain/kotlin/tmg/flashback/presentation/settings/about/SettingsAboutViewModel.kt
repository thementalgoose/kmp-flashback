package tmg.flashback.presentation.settings.about

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsAboutViewModel: ViewModel() {

    private val _uiState: MutableStateFlow<SettingsAboutUiState> = MutableStateFlow(
        SettingsAboutUiState()
    )
    val uiState: StateFlow<SettingsAboutUiState> = _uiState
}