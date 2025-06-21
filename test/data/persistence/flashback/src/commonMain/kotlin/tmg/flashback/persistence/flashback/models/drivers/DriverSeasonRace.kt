package tmg.flashback.persistence.flashback.models.drivers

fun DriverSeasonRace.Companion.model(
    driverId: String = "driverId",
    season: Int = 2020,
    round: Int = 1,
    constructorId: String = "constructorId",
    sprintQualifying: Boolean = false,
    sprintRace: Boolean = false,
    qualified: Int? = 1,
    gridPos: Int? = 1,
    finished: Int = 1,
    status: String = "status",
    points: Double = 1.0,
): DriverSeasonRace = DriverSeasonRace(
    driverId = driverId,
    season = season,
    round = round,
    constructorId = constructorId,
    sprintQualifying = sprintQualifying,
    sprintRace = sprintRace,
    qualified = qualified,
    gridPos = gridPos,
    finished = finished,
    status = status,
    points = points,
)