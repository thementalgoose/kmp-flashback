package tmg.flashback.presentation.settings.privacy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.analytics.repositories.AnalyticsRepository
import tmg.flashback.crashlytics.repositories.CrashlyticsRepository

class SettingsPrivacyViewModel(
    private val crashlyticsRepository: CrashlyticsRepository,
    private val analyticsRepository: AnalyticsRepository
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
    }

    fun updateAnalyticsEnabled(enabled: Boolean) {
        analyticsRepository.analyticsEnabled = enabled
        refresh()
    }
}