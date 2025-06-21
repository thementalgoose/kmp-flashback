package tmg.flashback.flashbackapi.api.models.drivers

fun DriverHistoryStanding.Companion.model(
    season: Int = 2020,
    championshipPosition: Int = 1,
    races: Map<String, DriverHistoryStandingRace> = mapOf(
        "r1" to DriverHistoryStandingRace.model()
    ),
    points: Double = 1.0,
    inProgress: Boolean = true
): DriverHistoryStanding = DriverHistoryStanding(
    season = season,
    championshipPosition = championshipPosition,
    races = races,
    points = points,
    inProgress = inProgress
)