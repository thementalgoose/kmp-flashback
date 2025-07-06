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
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState

class SettingsNotificationUpcomingViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase,
    private val permissionManager: PermissionManager
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsNotificationUpcomingUiState> = MutableStateFlow(SettingsNotificationUpcomingUiState(
        reminder = notificationSettingsRepository.notificationReminderPeriod,
        enabled = notificationSettingsRepository.notificationUpcomingEnabled
    ))
    val uiState: StateFlow<SettingsNotificationUpcomingUiState> = _uiState


    private val _permissionState: MutableStateFlow<PermissionState> = MutableStateFlow(PermissionState.NotDetermined)
    val permissionState: StateFlow<PermissionState> = _permissionState

    init {
        viewModelScope.launch {
            _permissionState.value = permissionManager.getPermissionState(Permission.Notifications)
        }
    }

    fun requestPermissions() {
        viewModelScope.launch {
            _permissionState.value = permissionManager.providePermission(Permission.Notifications)
        }
    }

    fun goToSettings() {
        permissionManager.openAppSettings()
    }

    fun refresh() {
        viewModelScope.launch {
            scheduleUpcomingNotificationsUseCase(true)
            _permissionState.value = permissionManager.providePermission(Permission.Notifications)
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