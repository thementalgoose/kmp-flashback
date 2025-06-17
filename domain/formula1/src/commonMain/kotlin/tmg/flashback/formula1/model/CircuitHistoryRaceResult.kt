package tmg.flashback.formula1.model

data class CircuitHistoryRaceResult(
    val position: Int,
    val driver: Driver,
    val constructor: Constructor
) {
    companion object
}