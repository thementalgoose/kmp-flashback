package tmg.flashback.feature.privacypolicy.presentation

import androidx.lifecycle.ViewModel
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.privacypolicy.repository.PrivacyRepository

class PrivacyPolicyViewModel(
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val privacyRepository: PrivacyRepository
): ViewModel() {

    fun openPolicy() {
        val policyUrl = privacyRepository.privacyPolicyUrl ?: return
        openWebpageUseCase(policyUrl)
    }

    fun openWebpage(url: String) {
        openWebpageUseCase(url)
    }
}