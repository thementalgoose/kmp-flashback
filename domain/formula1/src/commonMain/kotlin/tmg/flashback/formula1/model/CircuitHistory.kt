package tmg.flashback.formula1.model

data class CircuitHistory(
    val data: Circuit,
    val results: List<CircuitHistoryRace>
) {
    companion object
}