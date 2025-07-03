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
import kotlin.collections.minus
import kotlin.collections.plus

class SettingsNotificationResultsViewModel(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsNotificationResultsUiState> = MutableStateFlow(SettingsNotificationResultsUiState(
        enabled = notificationSettingsRepository.notificationResultsEnabled
    ))
    val uiState: StateFlow<SettingsNotificationResultsUiState> = _uiState

    fun refresh() {
        viewModelScope.launch {
            subscribeResultNotificationsUseCase()
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