package tmg.flashback.formula1.model

data class QualifyingResult(
    val entry: DriverEntry,
    val lapTime: LapTime?,
    val position: Int
) {
    companion object
}