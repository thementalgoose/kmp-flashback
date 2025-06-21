package tmg.flashback.persistence.flashback.models.standings

fun DriverStandingConstructor.Companion.model(
    driverId: String = "driverId",
    season: Int = 2020,
    constructorId: String = "constructorId",
    points: Double = 1.0,
): DriverStandingConstructor = DriverStandingConstructor(
    driverId = driverId,
    season = season,
    constructorId = constructorId,
    points = points
)