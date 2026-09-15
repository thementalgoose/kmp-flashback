package tmg.flashback.feature.season.presentation.shared.providedby

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.KoinViewModel
import tmg.flashback.data.repo.repository.InfoRepository

@KoinViewModel
class ProvidedByViewModel(
    private val infoRepository: InfoRepository,
): ViewModel() {

    val message: String?
        get() = infoRepository.dataProvidedBy
}