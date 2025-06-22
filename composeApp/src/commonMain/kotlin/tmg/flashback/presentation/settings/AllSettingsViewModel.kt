package tmg.flashback.presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.widget.upnext.usecases.IsWidgetsEnabledUseCase

class AllSettingsViewModel(
    isWidgetsEnabledUseCase: IsWidgetsEnabledUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<AllSettingsUiState> = MutableStateFlow(AllSettingsUiState(
        isRssEnabled = false,
        isThemeSupported = Device.isMonetThemeSupported,
        isWidgetsSupported = isWidgetsEnabledUseCase()
    ))
    val uiState: StateFlow<AllSettingsUiState> = _uiState
}