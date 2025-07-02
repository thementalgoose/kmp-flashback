package tmg.flashback.presentation.settings.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.analytics.repositories.AnalyticsRepository
import tmg.flashback.crashlytics.repositories.CrashlyticsRepository
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase

class SettingsNotificationUpcomingViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsNotificationUpcomingUiState> = MutableStateFlow(SettingsNotificationUpcomingUiState(
        reminder = notificationSettingsRepository.notificationReminderPeriod
    ))
    val uiState: StateFlow<SettingsNotificationUpcomingUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsNotificationUpcomingUiState(
                reminder = notificationSettingsRepository.notificationReminderPeriod,
            )
        }
    }

    fun notificationReminderClicked(reminder: NotificationReminder) {
        notificationSettingsRepository.notificationReminderPeriod = reminder
        viewModelScope.launch {
            scheduleUpcomingNotificationsUseCase(true)
        }
        refresh()
    }
}