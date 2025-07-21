package tmg.flashback.feature.circuits.presentation.circuit

import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.CircuitHistoryRace
import tmg.flashback.formula1.model.CircuitHistoryRaceResult

data class CircuitUiState(
    val isLoading: Boolean,
    val circuit: Circuit?,
    val trackLayout: TrackLayout?,
    val races: List<CircuitEvent>
)

data class CircuitEvent(
    val race: CircuitHistoryRace,
    val first: CircuitHistoryRaceResult?,
    val second: CircuitHistoryRaceResult?,
    val third: CircuitHistoryRaceResult?,
)