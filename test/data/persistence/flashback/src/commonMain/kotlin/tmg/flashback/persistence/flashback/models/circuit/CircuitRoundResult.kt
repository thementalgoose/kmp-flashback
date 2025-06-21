package tmg.flashback.persistence.flashback.models.circuit

fun CircuitRoundResult.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    position: Int = 1,
    driverId: String = "driverId",
    constructorId: String = "constructorId",
): CircuitRoundResult = CircuitRoundResult(
    season = season,
    round = round,
    position = position,
    driverId = driverId,
    constructorId = constructorId
)