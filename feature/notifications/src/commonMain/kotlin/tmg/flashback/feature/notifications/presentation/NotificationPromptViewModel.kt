package tmg.flashback.feature.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class NotificationPromptViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase,
    private val permissionManager: PermissionManager,
    private val openSettingsUseCase: OpenSettingsUseCase,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val _uiState: MutableStateFlow<Boolean> = MutableStateFlow(notificationSettingsRepository.notificationPromptSeen)
    val uiState: StateFlow<Boolean> = _uiState

    fun promptRuntimeNotifications() {
        viewModelScope.launch(coroutineContext) {
            val result = permissionManager.requestPermission(Permission.Notifications).await()
            scheduleUpcomingNotificationsUseCase()
            notificationSettingsRepository.notificationPromptSeen = true
            if (result == PermissionState.NotGranted) {
                openSettingsUseCase.openNotificationSettings()
            }
            _uiState.value = true
        }
    }

    fun close() {
        notificationSettingsRepository.notificationPromptSeen = true
        _uiState.value = true
    }
}