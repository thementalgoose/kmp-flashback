package tmg.flashback.presentation.settings.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.ui.theme.Theme
import tmg.flashback.ui.theme.ThemeManager

class SettingsThemeViewModel(
    private val themeManager: ThemeManager
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsThemeUIState> = MutableStateFlow(
        SettingsThemeUIState(theme = themeManager.currentTheme)
    )
    val uiState: StateFlow<SettingsThemeUIState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsThemeUIState(theme = themeManager.currentTheme)
        }
    }

    fun updateSelection(theme: Theme) {
        themeManager.currentTheme = theme
        refresh()
    }
}