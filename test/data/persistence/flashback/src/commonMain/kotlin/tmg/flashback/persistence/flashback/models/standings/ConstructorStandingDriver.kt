package tmg.flashback.persistence.flashback.models.standings

fun ConstructorStandingDriver.Companion.model(
    constructorId: String = "constructorId",
    season: Int = 2020,
    driverId: String = "driverId",
    points: Double = 1.0,
): ConstructorStandingDriver = ConstructorStandingDriver(
    constructorId = constructorId,
    season = season,
    driverId = driverId,
    points = points
)