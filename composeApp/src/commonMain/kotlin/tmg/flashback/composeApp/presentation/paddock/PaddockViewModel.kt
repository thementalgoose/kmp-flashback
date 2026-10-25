package tmg.flashback.composeApp.presentation.paddock

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.composeApp.repositories.NavRepository
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase

class PaddockViewModel(
    private val isRssEnabledUseCase: IsRssEnabledUseCase,
    private val navRepository: NavRepository,
    private val openWebpageUseCase: OpenWebpageUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<PaddockUiState> = MutableStateFlow(PaddockUiState(
        isRssEnabled = isRssEnabledUseCase.invoke(),
        navLinks = navRepository.navLinks
    ))
    val uiState: StateFlow<PaddockUiState> = _uiState

    fun openUrl(url: String, title: String = url) {
        openWebpageUseCase(url, title)
    }
}