package tmg.flashback.presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.webbrowser.usecases.IsInAppBrowserEnabledUseCase
import tmg.flashback.widgets.upnext.usecases.IsWidgetsEnabledUseCase

class AllSettingsViewModel(
    isWidgetsEnabledUseCase: IsWidgetsEnabledUseCase,
    isRssEnabledUseCase: IsRssEnabledUseCase,
    isInAppBrowserEnabledUseCase: IsInAppBrowserEnabledUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<AllSettingsUiState> = MutableStateFlow(AllSettingsUiState(
        isRssEnabled = isRssEnabledUseCase(),
        isThemeChangeSupported = Device.isMonetThemeSupported,
        isWidgetsSupported = isWidgetsEnabledUseCase(),
        isInAppBrowserSupported = isInAppBrowserEnabledUseCase(),
    ))
    val uiState: StateFlow<AllSettingsUiState> = _uiState
}