package tmg.flashback.flashbackapi.api.models.races

fun SprintRaceResult.Companion.model(
    driverId: String = "driverId",
    driverNumber: String = "23",
    constructorId: String = "constructorId",
    points: Double = 1.0,
    gridPos: Int? = 1,
    finished: Int = 1,
    status: String = "status",
    time: String? = "1:02:03.004"
): SprintRaceResult = SprintRaceResult(
    driverId = driverId,
    driverNumber = driverNumber,
    constructorId = constructorId,
    points = points,
    gridPos = gridPos,
    finished = finished,
    status = status,
    time = time,
)