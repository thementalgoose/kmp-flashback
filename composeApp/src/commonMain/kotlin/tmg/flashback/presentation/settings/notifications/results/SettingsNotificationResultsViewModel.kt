package tmg.flashback.presentation.settings.notifications.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState

class SettingsNotificationResultsViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase,
    private val permissionManager: PermissionManager
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsNotificationResultsUiState> = MutableStateFlow(SettingsNotificationResultsUiState(
        enabled = notificationSettingsRepository.notificationResultsEnabled
    ))
    val uiState: StateFlow<SettingsNotificationResultsUiState> = _uiState

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
            subscribeResultNotificationsUseCase()
            _permissionState.value = permissionManager.providePermission(Permission.Notifications)
        }
        _uiState.update {
            SettingsNotificationResultsUiState(
                enabled = notificationSettingsRepository.notificationResultsEnabled
            )
        }
    }

    fun setNotificationResult(upcoming: NotificationResultsAvailable, enabled: Boolean) {
        when (enabled) {
            true -> notificationSettingsRepository.notificationResultsEnabled += upcoming
            false -> notificationSettingsRepository.notificationResultsEnabled -= upcoming
        }
        refresh()
    }
}