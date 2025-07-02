package tmg.flashback.feature.about.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.device.repositories.DeviceRepository

class AboutViewModel(
    private val deviceRepository: DeviceRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<AboutUiState> = MutableStateFlow(AboutUiState(
        deviceUuid = deviceRepository.deviceUdid,
        contactEmail = deviceRepository.contactEmail
    ))
    val uiState: StateFlow<AboutUiState> = _uiState

}