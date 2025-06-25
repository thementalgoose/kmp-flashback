package tmg.flashback.feature.season.presentation.shared.providedby

import androidx.lifecycle.ViewModel
import tmg.flashback.data.repo.repository.InfoRepository

class ProvidedByViewModel(
    private val infoRepository: InfoRepository,
): ViewModel() {

    val message: String?
        get() = infoRepository.dataProvidedBy
}