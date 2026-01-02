package tmg.flashback.composeApp.presentation.settings.theme

import androidx.lifecycle.ViewModel
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_restart_app_required
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.style.theme.Theme
import tmg.flashback.style.theme.ThemeManager
import tmg.flashback.ui.toasts.ToastManager

class SettingsThemeViewModel(
    private val themeManager: ThemeManager,
    private val toastManager: ToastManager,
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
        toastManager.showMessage(string.settings_restart_app_required)
    }
}