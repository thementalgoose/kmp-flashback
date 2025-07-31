package tmg.flashback.presentation.settings.about

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.configuration.repositories.ConfigRepository
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.repositories.OnboardingRepository

class SettingsAboutViewModel(
    private val openStorePageUseCase: OpenStorePageUseCase,
    private val onboardingRepository: OnboardingRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsAboutUiState> = MutableStateFlow(
        SettingsAboutUiState()
    )
    val uiState: StateFlow<SettingsAboutUiState> = _uiState

    fun openReview() {
        openStorePageUseCase.invoke()
    }

    fun firstTimeSync() {
        onboardingRepository.initialSyncCompleted = false
    }
}