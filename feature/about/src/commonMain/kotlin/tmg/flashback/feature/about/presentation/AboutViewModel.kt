package tmg.flashback.feature.about.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.device.APPLE_STORE_LINK
import tmg.flashback.device.GITHUB_LINK
import tmg.flashback.device.PLAY_STORE_LINK
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.usecases.OpenEmailUseCase
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase

class AboutViewModel(
    private val deviceRepository: DeviceRepository,
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val openEmailUseCase: OpenEmailUseCase,
): ViewModel() {

    private val _uiState: MutableStateFlow<AboutUiState> = MutableStateFlow(AboutUiState(
        deviceUuid = deviceRepository.deviceUdid,
        installationId = deviceRepository.installationId,
        contactEmail = deviceRepository.contactEmail,
    ))
    val uiState: StateFlow<AboutUiState> = _uiState

    fun openDependency(dependency: AboutDependency) {
        openWebpageUseCase.invoke(dependency.url, dependency.name)
    }

    fun openButton(aboutButtons: AboutButtons) {
        when (aboutButtons) {
            AboutButtons.Play -> openWebpageUseCase(PLAY_STORE_LINK)
            AboutButtons.Apple -> openWebpageUseCase(APPLE_STORE_LINK)
            AboutButtons.Email -> openEmailUseCase(deviceRepository.contactEmail)
            AboutButtons.Github -> openWebpageUseCase(GITHUB_LINK)
        }
    }
}