package tmg.flashback.flashbackapi.api.models.races

fun SprintQualifyingResult.Companion.model(
    driverId: String = "driverId",
    driverNumber: String? = "driverNumber",
    constructorId: String = "constructorId",
    qualified: Int = 1,
    sq1: String? = "1:02.001",
    sq2: String? = "1:02.002",
    sq3: String? = "1:02.003"
): SprintQualifyingResult = SprintQualifyingResult(
    driverId = driverId,
    driverNumber = driverNumber,
    constructorId = constructorId,
    qualified = qualified,
    sq1 = sq1,
    sq2 = sq2,
    sq3 = sq3
)