package tmg.flashback.formula1.model

import tmg.flashback.formula1.enums.RaceStatus

data class DriverHistorySeasonRace(
    val isSprint: Boolean,
    val status: RaceStatus,
    val finished: Int,
    val points: Double,
    val qualified: Int?,
    val gridPos: Int?,
    val constructor: Constructor?,
    val raceInfo: RaceInfo
) {
    companion object
}