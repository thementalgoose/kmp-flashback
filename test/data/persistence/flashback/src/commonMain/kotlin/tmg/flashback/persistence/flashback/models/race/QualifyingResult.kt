package tmg.flashback.persistence.flashback.models.race

fun QualifyingResult.Companion.model(
    driverId: String = "driverId",
    season: Int = 2020,
    round: Int = 1,
    constructorId: String = "constructorId",
    qualified: Int = 1,
    q1: String? = "1:02.001",
    q2: String? = "1:02.002",
    q3: String? = "1:02.003"
): QualifyingResult = QualifyingResult(
    driverId = driverId,
    season = season,
    round = round,
    constructorId = constructorId,
    qualified = qualified,
    q1 = q1,
    q2 = q2,
    q3 = q3
)