package tmg.flashback.feature.circuits.presentation.circuit

import tmg.flashback.formula1.model.Circuit

data class CircuitUiState(
    val isLoading: Boolean,
    val circuit: Circuit?
) {
}