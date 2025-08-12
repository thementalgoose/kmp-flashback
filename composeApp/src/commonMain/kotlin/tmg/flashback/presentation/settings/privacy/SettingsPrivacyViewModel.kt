package tmg.flashback.presentation.settings.privacy

import androidx.lifecycle.ViewModel
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_restart_app_required
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.analytics.repositories.AnalyticsRepository
import tmg.flashback.crashlytics.repositories.CrashlyticsRepository
import tmg.flashback.ui.toasts.ToastManager

class SettingsPrivacyViewModel(
    private val crashlyticsRepository: CrashlyticsRepository,
    private val analyticsRepository: AnalyticsRepository,
    private val toastManager: ToastManager
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsPrivacyUiState> = MutableStateFlow(SettingsPrivacyUiState(
        crashReportingEnabled = crashlyticsRepository.crashlyticsEnabled,
        analyticsEnabled = analyticsRepository.analyticsEnabled
    ))
    val uiState: StateFlow<SettingsPrivacyUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsPrivacyUiState(
                crashReportingEnabled = crashlyticsRepository.crashlyticsEnabled,
                analyticsEnabled = analyticsRepository.analyticsEnabled
            )
        }
    }

    fun updateCrashlyticsEnabled(enabled: Boolean) {
        crashlyticsRepository.crashlyticsEnabled = enabled
        refresh()
        toastManager.showMessage(string.settings_restart_app_required)
    }

    fun updateAnalyticsEnabled(enabled: Boolean) {
        analyticsRepository.analyticsEnabled = enabled
        refresh()
        toastManager.showMessage(string.settings_restart_app_required)
    }
}