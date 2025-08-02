package tmg.flashback.presentation.settings.notifications.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.ui.permissions.Permission.Notifications
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import tmg.flashback.ui.permissions.PermissionState.NotDetermined
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class SettingsNotificationResultsViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase,
    private val permissionManager: PermissionManager,
    private val openSettingsUseCase: OpenSettingsUseCase,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsNotificationResultsUiState> = MutableStateFlow(SettingsNotificationResultsUiState(
        enabled = notificationSettingsRepository.notificationResultsEnabled
    ))
    val uiState: StateFlow<SettingsNotificationResultsUiState> = _uiState

    private val _permissionState: MutableStateFlow<PermissionState> = MutableStateFlow(NotDetermined)
    val permissionState: StateFlow<PermissionState> = _permissionState

    init {
        viewModelScope.launch(coroutineContext) {
            _permissionState.value = permissionManager.getPermissionState(Notifications)
        }
    }

    fun requestPermissions() {
        viewModelScope.launch(coroutineContext) {
            val result = permissionManager.requestPermission(Notifications).await()
            if (result != PermissionState.Granted) {
                goToSettings()
            } else {
                reschedule()
                _uiState.update {
                    SettingsNotificationResultsUiState(
                        enabled = notificationSettingsRepository.notificationResultsEnabled
                    )
                }
            }
        }
    }

    fun goToSettings() {
        openSettingsUseCase.openNotificationSettings()
    }

    fun refresh() {
        viewModelScope.launch(coroutineContext) {
            reschedule()
        }
        _uiState.update {
            SettingsNotificationResultsUiState(
                enabled = notificationSettingsRepository.notificationResultsEnabled
            )
        }
    }
    private suspend fun reschedule() {
            subscribeResultNotificationsUseCase()
            _permissionState.value = permissionManager.getPermissionState(Notifications)

    }

    fun setNotificationResult(upcoming: NotificationResultsAvailable, enabled: Boolean) {
        when (enabled) {
            true -> notificationSettingsRepository.notificationResultsEnabled += upcoming
            false -> notificationSettingsRepository.notificationResultsEnabled -= upcoming
        }
        refresh()
    }
}