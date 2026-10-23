package tmg.flashback.composeApp.presentation.info

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.flashbackapi.api.repositories.NetworkConfigRepository

class InfoViewModel(
    private val networkConfigRepository: NetworkConfigRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<InfoUiState> = MutableStateFlow(InfoUiState())
    val uiState: StateFlow<InfoUiState> = _uiState

    init {
        refresh()
    }

    fun refresh() {
        val value = _uiState.value
        _uiState.update {
            value.copy(
                baseUrl = networkConfigRepository.baseUrl
            )
        }
    }
}