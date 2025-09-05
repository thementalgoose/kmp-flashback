package tmg.flashback.feature.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.Permission.Notifications
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import tmg.flashback.ui.permissions.PermissionState.NotDetermined
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class NotificationPromptViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase,
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase,
    private val permissionManager: PermissionManager,
    private val openSettingsUseCase: OpenSettingsUseCase,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val _promptNotification: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val promptNotification: StateFlow<Boolean> = _promptNotification

    init {
        viewModelScope.launch(coroutineContext) {
            val existingPermission = permissionManager.getPermissionState(Notifications)
            _promptNotification.value = !notificationSettingsRepository.notificationPromptSeen && existingPermission == NotDetermined
        }
    }

    fun promptRuntimeNotifications() {
        viewModelScope.launch(coroutineContext) {
            val result = permissionManager.requestPermission(Notifications).await()
            _promptNotification.value = false
            notificationSettingsRepository.notificationPromptSeen = true
            if (result == PermissionState.NotGranted) {
                openSettingsUseCase.openNotificationSettings()
            } else {
                scheduleUpcomingNotificationsUseCase()
                subscribeResultNotificationsUseCase()
            }
        }
    }

    fun close() {
        notificationSettingsRepository.notificationPromptSeen = true
        _promptNotification.value = false
    }
}