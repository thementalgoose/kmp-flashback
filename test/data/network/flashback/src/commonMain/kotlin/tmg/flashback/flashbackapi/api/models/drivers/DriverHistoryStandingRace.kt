package tmg.flashback.flashbackapi.api.models.drivers

import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.constructors.model
import tmg.flashback.flashbackapi.api.models.races.RaceData
import tmg.flashback.flashbackapi.api.models.races.model

fun DriverHistoryStandingRace.Companion.model(
    constructor: Constructor = Constructor.model(),
    race: RaceData = RaceData.model(),
    sprint: DriverHistoryStandingRaceSprint? = DriverHistoryStandingRaceSprint.model(),
    qualified: Int = 1,
    gridPos: Int? = 1,
    finished: Int = 1,
    status: String = "status",
    points: Double = 1.0,
): DriverHistoryStandingRace = DriverHistoryStandingRace(
    constructor = constructor,
    race = race,
    sprint = sprint,
    qualified = qualified,
    gridPos = gridPos,
    finished = finished,
    status = status,
    points = points,
)