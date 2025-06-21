package tmg.flashback.flashbackapi.api.models.circuits

fun CircuitResultRace.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    name: String = "name",
    date: String = "1995-10-12",
    time: String? = "12:34",
    wikiUrl: String? = "wikiUrl"
): CircuitResultRace = CircuitResultRace(
    season = season,
    round = round,
    name = name,
    date = date,
    time = time,
    wikiUrl = wikiUrl
)