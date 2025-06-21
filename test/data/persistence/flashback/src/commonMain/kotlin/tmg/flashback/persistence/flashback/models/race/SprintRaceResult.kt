package tmg.flashback.persistence.flashback.models.race

fun SprintRaceResult.Companion.model(
    driverId: String = "driverId",
    season: Int = 2020,
    round: Int = 1,
    constructorId: String = "constructorId",
    points: Double = 1.0,
    gridPos: Int? = 1,
    finished: Int = 1,
    status: String = "status",
    time: String = "1:02:03.004"
): SprintRaceResult = SprintRaceResult(
    driverId = driverId,
    season = season,
    round = round,
    constructorId = constructorId,
    points = points,
    gridPos = gridPos,
    finished = finished,
    status = status,
    time = time
)