package tmg.flashback.presentation.settings.darkmode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.style.theme.NightMode
import tmg.flashback.style.theme.ThemeManager

class SettingsDarkModeViewModel(
    private val themeManager: ThemeManager
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsDarkModeUiState> = MutableStateFlow(
        SettingsDarkModeUiState(nightMode = themeManager.currentNightMode)
    )
    val uiState: StateFlow<SettingsDarkModeUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsDarkModeUiState(nightMode = themeManager.currentNightMode)
        }
    }

    fun updateSelection(nightMode: NightMode) {
        themeManager.currentNightMode = nightMode
        refresh()
    }
}