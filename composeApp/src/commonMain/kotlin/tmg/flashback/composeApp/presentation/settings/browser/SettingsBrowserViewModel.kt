package tmg.flashback.composeApp.presentation.settings.browser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.webbrowser.repository.WebRepository

class SettingsBrowserViewModel(
    private val webRepository: WebRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsBrowserUiState> = MutableStateFlow(
        SettingsBrowserUiState(
            enabled = webRepository.enabled,
            enableJavascript = webRepository.enableJavascript,
            toolbarAtTop = webRepository.toolbarAtTop
        )
    )
    val uiState: StateFlow<SettingsBrowserUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsBrowserUiState(
                enabled = webRepository.enabled,
                enableJavascript = webRepository.enableJavascript,
                toolbarAtTop = webRepository.toolbarAtTop
            )
        }
    }

    fun updateEnabled(enabled: Boolean) {
        webRepository.enabled = enabled
        refresh()
    }

    fun updateEnableJavascript(enabled: Boolean) {
        webRepository.enableJavascript = enabled
        refresh()
    }

    fun updateToolbarTop(enabled: Boolean) {
        webRepository.toolbarAtTop = enabled
        refresh()
    }

}