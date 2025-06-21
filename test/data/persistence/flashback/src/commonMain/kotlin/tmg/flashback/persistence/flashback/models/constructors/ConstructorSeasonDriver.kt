package tmg.flashback.persistence.flashback.models.constructors

fun ConstructorSeasonDriver.Companion.model(
    constructorId: String = "constructorId",
    season: Int = 2020,
    driverId: String = "driverId",
    points: Double = 1.0,
    championshipPosition: Int? = 1,
    wins: Int = 1,
    races: Int = 1,
    podiums: Int = 1,
    pointsFinishes: Int = 1,
    pole: Int = 1,
): ConstructorSeasonDriver = ConstructorSeasonDriver(
    constructorId = constructorId,
    season = season,
    driverId = driverId,
    points = points,
    championshipPosition = championshipPosition,
    wins = wins,
    races = races,
    podiums = podiums,
    pointsFinishes = pointsFinishes,
    pole = pole,
)