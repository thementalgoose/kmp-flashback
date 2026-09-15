package tmg.flashback.feature.privacypolicy.presentation

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.privacypolicy.repository.PrivacyRepository

@KoinViewModel
class PrivacyPolicyViewModel(
    @Provided private val openWebpageUseCase: OpenWebpageUseCase,
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