package tmg.flashback.composeApp.presentation.settings.notifications.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.model.NotificationNews
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.SubscribeNewsNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.ui.permissions.Permission.Notifications
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import tmg.flashback.ui.permissions.PermissionState.NotDetermined
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@KoinViewModel
class SettingsNotificationNewsViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val subscribeNewsNotificationsUseCase: SubscribeNewsNotificationsUseCase,
    private val permissionManager: PermissionManager,
    private val openSettingsUseCase: OpenSettingsUseCase,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsNotificationNewsUiState> = MutableStateFlow(SettingsNotificationNewsUiState(
        enabled = notificationSettingsRepository.notificationNewsEnabled
    ))
    val uiState: StateFlow<SettingsNotificationNewsUiState> = _uiState

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
                    SettingsNotificationNewsUiState(
                        enabled = notificationSettingsRepository.notificationNewsEnabled
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
            SettingsNotificationNewsUiState(
                enabled = notificationSettingsRepository.notificationNewsEnabled
            )
        }
    }
    private suspend fun reschedule() {
        subscribeNewsNotificationsUseCase()
        _permissionState.value = permissionManager.getPermissionState(Notifications)
    }

    fun setNotificationNews(upcoming: NotificationNews, enabled: Boolean) {
        when (enabled) {
            true -> notificationSettingsRepository.notificationNewsEnabled += upcoming
            false -> notificationSettingsRepository.notificationNewsEnabled -= upcoming
        }
        refresh()
    }
}