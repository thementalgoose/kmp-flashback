package tmg.flashback.feature.privacypolicy.presentation

import androidx.lifecycle.ViewModel
import tmg.flashback.device.usecases.OpenWebpageUseCase

class PrivacyPolicyViewModel(
    private val openWebpageUseCase: OpenWebpageUseCase
): ViewModel() {

    fun openWebpage(url: String) {
        openWebpageUseCase(url)
    }
}