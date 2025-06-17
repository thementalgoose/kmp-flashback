package tmg.flashback.formula1.model

import tmg.flashback.formula1.enums.RaceStatus

data class SprintRaceResult(
    val entry: DriverEntry,
    val time: LapTime?,
    val points: Double,
    val grid: Int?,
    val finish: Int,
    val status: RaceStatus
) {
    companion object
}