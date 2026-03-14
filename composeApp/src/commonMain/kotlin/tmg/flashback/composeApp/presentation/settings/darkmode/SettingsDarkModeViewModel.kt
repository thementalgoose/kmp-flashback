package tmg.flashback.composeApp.presentation.settings.darkmode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.analytics.usecases.LogEventUseCase
import tmg.flashback.style.theme.NightMode
import tmg.flashback.style.theme.ThemeManager

class SettingsDarkModeViewModel(
    private val themeManager: ThemeManager,
    private val logEventUseCase: LogEventUseCase
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
        logEventUseCase.logEvent("change_appearance", mapOf("night_mode" to nightMode.label))
        themeManager.currentNightMode = nightMode
        refresh()
    }

    private val NightMode.label: String
        get() = when (this) {
            NightMode.DEFAULT -> "auto"
            NightMode.DAY -> "day"
            NightMode.NIGHT -> "night"
        }
}