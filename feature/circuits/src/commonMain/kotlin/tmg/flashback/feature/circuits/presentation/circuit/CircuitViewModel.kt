package tmg.flashback.feature.circuits.presentation.circuit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.CircuitRepository

class CircuitViewModel(
    private val circuitRepository: CircuitRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<CircuitUiState> = MutableStateFlow(CircuitUiState(
        isLoading = false,
        circuit = null,
    ))
    val uiState: StateFlow<CircuitUiState> = _uiState

    fun refresh() {
        viewModelScope.launch {
            circuitRepository.populateCircuits()
        }
    }
}