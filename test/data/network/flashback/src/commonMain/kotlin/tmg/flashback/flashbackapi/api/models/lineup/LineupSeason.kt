package tmg.flashback.flashbackapi.api.models.lineup

fun LineupSeason.Companion.model(
    season: Int = 2020,
    drivers: Map<String, String> = mapOf("driverId" to "constructorId")
): LineupSeason = LineupSeason(
    season = season,
    drivers = drivers
)