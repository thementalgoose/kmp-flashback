package tmg.flashback.feature.circuits.presentation.circuit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.device.usecases.OpenLocationUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Location
import tmg.flashback.infrastructure.datetime.now
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CircuitViewModel(
    private val circuitRepository: CircuitRepository,
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val openLocationUseCase: OpenLocationUseCase,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val _uiState: MutableStateFlow<CircuitUiState> = MutableStateFlow(CircuitUiState(
        isLoading = false,
        circuit = null,
        trackLayout = null,
        races = emptyList()
    ))
    val uiState: StateFlow<CircuitUiState> = _uiState

    fun load(circuitId: String) {
        _uiState.update {
            it.copy(
                isLoading = false,
                circuit = null,
                trackLayout = null,
                races = emptyList()
            )
        }
        viewModelScope.launch(coroutineContext) {
            if (!populate(circuitId)) {
                _uiState.update { it.copy(isLoading = true) }
                circuitRepository.populateCircuit(circuitId)
                _uiState.update { it.copy(isLoading = false) }
                populate(circuitId)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(coroutineContext) {
            _uiState.update { it.copy(isLoading = true) }
            circuitRepository.populateCircuits()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun populate(circuitId: String): Boolean {
        val history = circuitRepository.getCircuitHistory(circuitId).firstOrNull()
        _uiState.update {
            it.copy(
                circuit = history?.data,
                trackLayout = TrackLayout.getTrack(circuitId),
                races = (history?.results ?: emptyList())
                    .map {
                        CircuitEvent(
                            race = it,
                            first = it.preview.firstOrNull { it.position == 1 },
                            second = it.preview.firstOrNull { it.position == 2 },
                            third = it.preview.firstOrNull { it.position == 3 }
                        )
                    }
                    .sortedByDescending { it.race.season }
            )
        }
        return false
    }

    fun openLink(link: String) {
        openWebpageUseCase(link)
    }

    fun openMap(location: Location, name: String) {
        openLocationUseCase(
            lat = location.lat,
            lng = location.lng,
            name = name,
        )
    }
}