package tmg.flashback.formula1.model

import tmg.flashback.formula1.enums.RaceStatus

fun DriverHistorySeasonRace.Companion.model(
    isSprint: Boolean = false,
    status: RaceStatus = RaceStatus.from("status"),
    finished: Int = 1,
    points: Double = 1.0,
    qualified: Int? = 1,
    gridPos: Int? = 1,
    constructor: Constructor = Constructor.model(),
    raceInfo: RaceInfo = RaceInfo.model(),
): DriverHistorySeasonRace = DriverHistorySeasonRace(
    isSprint = isSprint,
    status = status,
    finished = finished,
    points = points,
    qualified = qualified,
    gridPos = gridPos,
    constructor = constructor,
    raceInfo = raceInfo
)