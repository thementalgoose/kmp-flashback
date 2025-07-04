package tmg.flashback.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tmg.flashback.device.models.Permission
import tmg.flashback.device.models.PermissionState
import tmg.flashback.device.usecases.IsPermissionGrantedUseCase
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.webbrowser.usecases.IsInAppBrowserEnabledUseCase
import tmg.flashback.widgets.upnext.usecases.IsWidgetsEnabledUseCase

class AllSettingsViewModel(
    isWidgetsEnabledUseCase: IsWidgetsEnabledUseCase,
    isRssEnabledUseCase: IsRssEnabledUseCase,
    isInAppBrowserEnabledUseCase: IsInAppBrowserEnabledUseCase,
    private val isPermissionEnabledUseCase: IsPermissionGrantedUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<AllSettingsUiState> = MutableStateFlow(AllSettingsUiState(
        isRssEnabled = isRssEnabledUseCase(),
        isThemeSupported = Device.isMonetThemeSupported,
        isWidgetsSupported = isWidgetsEnabledUseCase(),
        isInAppBrowserSupported = isInAppBrowserEnabledUseCase()
    ))
    val uiState: StateFlow<AllSettingsUiState> = _uiState

    private val _notificationPermissionEnabled: MutableStateFlow<PermissionState> = MutableStateFlow(PermissionState.Unknown)
    val notificationPermissionEnabled: StateFlow<PermissionState> = _notificationPermissionEnabled

    fun refresh() {
        viewModelScope.launch {
            _notificationPermissionEnabled.value = isPermissionEnabledUseCase(Permission.Notifications)
        }
    }
}