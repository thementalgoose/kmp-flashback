package tmg.flashback.flashbackapi.api.models.races

fun QualifyingResult.Companion.model(
    driverId: String = "driverId",
    driverNumber: String? = "driverNumber",
    constructorId: String = "constructorId",
    points: Double? = 1.0,
    qualified: Int = 1,
    q1: String? = "1:02.001",
    q2: String? = "1:02.002",
    q3: String? = "1:02.003"
): QualifyingResult = QualifyingResult(
    driverId = driverId,
    driverNumber = driverNumber,
    constructorId = constructorId,
    points = points,
    qualified = qualified,
    q1 = q1,
    q2 = q2,
    q3 = q3
)