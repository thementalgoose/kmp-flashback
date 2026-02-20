package tmg.flashback.webbrowser.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.device.usecases.ShareWebpageUseCase
import tmg.flashback.webbrowser.repository.WebRepository

data class WebViewUiState(
    val toolbarAtTop: Boolean
)

class WebViewViewModel(
    webRepository: WebRepository,
    private val shareWebpageUseCase: ShareWebpageUseCase,
    private val openWebpageUseCase: OpenWebpageUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<WebViewUiState> = MutableStateFlow(WebViewUiState(
        toolbarAtTop = webRepository.toolbarAtTop
    ))
    val uiState: StateFlow<WebViewUiState> = _uiState

    fun share(url: String) {
        shareWebpageUseCase.invoke(url)
    }

    fun open(url: String) {
        openWebpageUseCase.invoke(url)
    }
}