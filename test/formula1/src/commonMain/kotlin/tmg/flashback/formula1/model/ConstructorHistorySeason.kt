package tmg.flashback.formula1.model

fun ConstructorHistorySeason.Companion.model(
    season: Int = 2020,
    races: Int = 1,
    drivers: Map<String, ConstructorHistorySeasonDriver> = mapOf(
        "driverId" to ConstructorHistorySeasonDriver.model()
    ),
    isInProgress: Boolean = true,
    championshipStanding: Int? = 1,
    inProgressMaxPosition: Int? = 1,
    inProgressMinPosition: Int? = 1,
    points: Double = 1.0,
): ConstructorHistorySeason = ConstructorHistorySeason(
    drivers = drivers,
    isInProgress = isInProgress,
    inProgressMinPosition = inProgressMinPosition,
    inProgressMaxPosition = inProgressMaxPosition,
    championshipStanding = championshipStanding,
    points = points,
    season = season,
    races = races
)