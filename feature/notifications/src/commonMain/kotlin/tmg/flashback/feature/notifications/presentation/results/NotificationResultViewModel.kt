package tmg.flashback.feature.notifications.presentation.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.IsNotificationEnabledUseCase
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeNewsNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.ui.permissions.Permission.Notifications
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@KoinViewModel
class NotificationResultViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase,
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase,
    private val subscribeNewsNotificationsUseCase: SubscribeNewsNotificationsUseCase,
    private val isNotificationEnabledUseCase: IsNotificationEnabledUseCase,
    private val permissionManager: PermissionManager,
    private val openSettingsUseCase: OpenSettingsUseCase,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val _promptNotification: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val promptNotification: StateFlow<Boolean> = _promptNotification

    fun forResult(resultsAvailable: NotificationResultsAvailable) {
        viewModelScope.launch(coroutineContext) {
            val isPermissionEnabled = isNotificationEnabledUseCase.invoke(resultsAvailable)
            _promptNotification.value = !notificationSettingsRepository.notificationPromptResultAvailableSeen.contains(resultsAvailable)
                    && !isPermissionEnabled
        }
    }

    fun promptRuntimeNotifications(resultsAvailable: NotificationResultsAvailable) {
        viewModelScope.launch(coroutineContext) {
            val result = permissionManager.requestPermission(Notifications).await()
            _promptNotification.value = false
            notificationSettingsRepository.notificationPromptResultAvailableSeen += resultsAvailable
            if (result == PermissionState.Granted) {
                scheduleUpcomingNotificationsUseCase()
                subscribeResultNotificationsUseCase()
                subscribeNewsNotificationsUseCase()
            }
            openSettingsUseCase.openNotificationSettings()
        }
    }

    fun close(resultsAvailable: NotificationResultsAvailable) {
        notificationSettingsRepository.notificationPromptResultAvailableSeen += resultsAvailable
        _promptNotification.value = false
    }
}