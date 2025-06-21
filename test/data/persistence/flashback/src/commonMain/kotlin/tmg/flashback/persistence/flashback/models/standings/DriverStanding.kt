package tmg.flashback.persistence.flashback.models.standings

fun DriverStanding.Companion.model(
    driverId: String = "driverId",
    season: Int = 2020,
    points: Double = 1.0,
    position: Int? = 1,
    inProgress: Boolean = true,
    inProgressName: String? = "name",
    inProgressRound: Int? = 1,
    races: Int = 1,
): DriverStanding = DriverStanding(
    driverId = driverId,
    season = season,
    points = points,
    position = position,
    inProgress = inProgress,
    inProgressName = inProgressName,
    inProgressRound = inProgressRound,
    races = races
)