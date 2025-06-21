package tmg.flashback.persistence.flashback.models.race

fun SprintQualifyingResult.Companion.model(
    driverId: String = "driverId",
    season: Int = 2020,
    round: Int = 1,
    constructorId: String = "constructorId",
    qualified: Int = 1,
    sq1: String? = "1:02.001",
    sq2: String? = "1:02.002",
    sq3: String? = "1:02.003"
): SprintQualifyingResult = SprintQualifyingResult(
    driverId = driverId,
    season = season,
    round = round,
    constructorId = constructorId,
    qualified = qualified,
    sq1 = sq1,
    sq2 = sq2,
    sq3 = sq3
)