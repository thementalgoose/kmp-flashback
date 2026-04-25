package tmg.flashback.persistence.flashback.models.race

fun RaceInfo.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    name: String = "name",
    date: String = "2020-10-12",
    circuitId: String = "circuitId",
    laps: String? = "12",
    time: String? = "12:34",
    cancelled: Boolean = false,
    wikiUrl: String? = "wikiUrl",
    aerialUrl: String? = "aerialUrl",
    youtube: String? = "youtube"
): RaceInfo = RaceInfo(
    season = season,
    round = round,
    name = name,
    date = date,
    laps = laps,
    circuitId = circuitId,
    time = time,
    wikiUrl = wikiUrl,
    youtube = youtube,
    aerialUrl = aerialUrl,
    cancelled = cancelled,
)