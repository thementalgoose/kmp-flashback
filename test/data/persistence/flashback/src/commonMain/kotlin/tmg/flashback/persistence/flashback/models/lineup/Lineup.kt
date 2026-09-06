package tmg.flashback.persistence.flashback.models.lineup

fun Lineup.Companion.model(
    constructorId: String = "constructorId",
    driverId: String = "driverId",
    season: Int = 2020
): Lineup = Lineup(
    constructorId = constructorId,
    driverId = driverId,
    season = season
)