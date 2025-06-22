package tmg.flashback.presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.widget.upnext.usecases.IsWidgetsEnabledUseCase

class AllSettingsViewModel(
    isWidgetsEnabledUseCase: IsWidgetsEnabledUseCase,
    isRssEnabledUseCase: IsRssEnabledUseCase,
): ViewModel() {

    private val _uiState: MutableStateFlow<AllSettingsUiState> = MutableStateFlow(AllSettingsUiState(
        isRssEnabled = isRssEnabledUseCase(),
        isThemeSupported = Device.isMonetThemeSupported,
        isWidgetsSupported = isWidgetsEnabledUseCase()
    ))
    val uiState: StateFlow<AllSettingsUiState> = _uiState
}