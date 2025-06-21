package tmg.flashback.persistence.flashback.models.drivers

fun DriverSeason.Companion.model(
    driverId: String = "driverId",
    season: Int = 2020,
    championshipStanding: Int? = 1,
    points: Double = 1.0,
    inProgress: Boolean = true
): DriverSeason = DriverSeason(
    driverId = driverId,
    season = season,
    championshipStanding = championshipStanding,
    points = points,
    inProgress = inProgress
)