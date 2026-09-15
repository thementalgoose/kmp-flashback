package tmg.flashback.composeApp.presentation.settings.about

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_pref_reset_toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.device.GITHUB_RELEASE_LINK
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.usecases.OpenEmailUseCase
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.composeApp.repositories.OnboardingRepository
import tmg.flashback.ui.toasts.ToastManager

@KoinViewModel
class SettingsAboutViewModel(
    @Provided private val openStorePageUseCase: OpenStorePageUseCase,
    @Provided private val openEmailUseCase: OpenEmailUseCase,
    @Provided private val openWebpageUseCase: OpenWebpageUseCase,
    private val onboardingRepository: OnboardingRepository,
    private val deviceRepository: DeviceRepository,
    private val toastManager: ToastManager
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsAboutUiState> = MutableStateFlow(
        SettingsAboutUiState()
    )
    val uiState: StateFlow<SettingsAboutUiState> = _uiState

    fun openReview() {
        openStorePageUseCase.invoke()
    }

    fun openFeedback() {
        openEmailUseCase.invoke(
            email = deviceRepository.contactEmail,
            title = "Flashback - Feedback",
            contents = "\n\n${Device.string()}\nID: ${deviceRepository.deviceUdid}\nIID: ${deviceRepository.installationId}"
        )
    }

    fun openChangelog() {
        openWebpageUseCase.invoke(GITHUB_RELEASE_LINK)
    }

    fun firstTimeSync() {
        onboardingRepository.initialSyncCompleted = false
        toastManager.showMessage(string.settings_pref_reset_toast)
    }
}