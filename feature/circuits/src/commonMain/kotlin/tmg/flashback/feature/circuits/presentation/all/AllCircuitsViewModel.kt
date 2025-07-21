package tmg.flashback.feature.circuits.presentation.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.infrastructure.log.logInfo
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class AllCircuitsViewModel(
    private val circuitRepository: CircuitRepository,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val searchTerm: MutableStateFlow<String?> = MutableStateFlow(null)
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val uiState: StateFlow<AllCircuitsUiState> =
        combine(
            circuitRepository
                .getCircuits()
                .map { circuits -> circuits
                    .map {
                        CircuitOverview(
                            circuit = it,
                            track = TrackLayout.getTrack(it.id)
                        )
                    }
                    .sortedBy { it.circuit.name.lowercase() }
                },
            searchTerm,
            isLoading
        ) { circuits, searchTerm, isLoading ->
            AllCircuitsUiState(
                isLoading = isLoading,
                searchQuery = searchTerm,
                circuits = circuits
                    .filter { it.searchTerm.contains(searchTerm?.lowercase() ?: "") }
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, AllCircuitsUiState())

    fun enterSearchTerm(search: String) {
        searchTerm.update { search }
    }

    fun clearSearch() {
        searchTerm.update { null }
    }

    fun refresh() {
        viewModelScope.launch(coroutineContext) {
            isLoading.update { true }
            circuitRepository.populateCircuits()
            isLoading.update { false }
        }
    }
}