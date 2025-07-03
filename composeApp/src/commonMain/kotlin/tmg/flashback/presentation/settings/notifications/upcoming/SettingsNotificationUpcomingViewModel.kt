package tmg.flashback.presentation.settings.notifications.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase

class SettingsNotificationUpcomingViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsNotificationUpcomingUiState> = MutableStateFlow(SettingsNotificationUpcomingUiState(
        reminder = notificationSettingsRepository.notificationReminderPeriod,
        enabled = notificationSettingsRepository.notificationUpcomingEnabled
    ))
    val uiState: StateFlow<SettingsNotificationUpcomingUiState> = _uiState

    fun refresh() {
        viewModelScope.launch {
            scheduleUpcomingNotificationsUseCase(true)
        }
        _uiState.update {
            SettingsNotificationUpcomingUiState(
                reminder = notificationSettingsRepository.notificationReminderPeriod,
                enabled = notificationSettingsRepository.notificationUpcomingEnabled
            )
        }
    }

    fun notificationReminderClicked(reminder: NotificationReminder) {
        notificationSettingsRepository.notificationReminderPeriod = reminder
        refresh()
    }

    fun setNotificationUpcoming(upcoming: NotificationUpcoming, enabled: Boolean) {
        when (enabled) {
            true -> notificationSettingsRepository.notificationUpcomingEnabled += upcoming
            false -> notificationSettingsRepository.notificationUpcomingEnabled -= upcoming
        }
        refresh()
    }
}