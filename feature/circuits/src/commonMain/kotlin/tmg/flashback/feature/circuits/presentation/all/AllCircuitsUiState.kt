package tmg.flashback.feature.circuits.presentation.all

import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit

data class AllCircuitsUiState(
    val isLoading: Boolean = false,
    val searchQuery: String? = null,
    val circuits: List<CircuitOverview> = emptyList()
)

data class CircuitOverview(
    val circuit: Circuit,
    val track: TrackLayout?
) {
    val searchTerm: String by lazy {
        "${circuit.name.lowercase()} ${circuit.city.lowercase()} ${circuit.country.lowercase()}"
    }
}