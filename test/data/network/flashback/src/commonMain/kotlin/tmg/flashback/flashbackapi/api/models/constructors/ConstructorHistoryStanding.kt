package tmg.flashback.flashbackapi.api.models.constructors

fun ConstructorHistoryStanding.Companion.model(
    season: Int = 2020,
    championshipPosition: Int? = 1,
    points: Double? = 1.0,
    inProgress: Boolean = true,
    inProgressMaxPosition: Int? = 1,
    inProgressMinPosition: Int? = 1,
    races: Int? = 1,
    drivers: Map<String, ConstructorHistoryStandingDriver> = mapOf(
        "driverId" to ConstructorHistoryStandingDriver.model()
    )
): ConstructorHistoryStanding = ConstructorHistoryStanding(
    season = season,
    championshipPosition = championshipPosition,
    points = points,
    inProgress = inProgress,
    inProgressMaxPosition = inProgressMaxPosition,
    inProgressMinPosition = inProgressMinPosition,
    races = races,
    drivers = drivers
)