package tmg.flashback.webbrowser.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.webbrowser.repository.WebRepository

data class WebViewUiState(
    val toolbarAtTop: Boolean
)

class WebViewViewModel(
    webRepository: WebRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<WebViewUiState> = MutableStateFlow(WebViewUiState(
        toolbarAtTop = webRepository.toolbarAtTop
    ))
    val uiState: StateFlow<WebViewUiState> = _uiState
}