package tmg.flashback.flashbackapi.api.models.races

import tmg.flashback.flashbackapi.api.models.circuits.Circuit
import tmg.flashback.flashbackapi.api.models.circuits.model

fun RaceData.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    name: String = "name",
    date: String = "2020-10-12",
    time: String? = "12:34",
    laps: String? = "12",
    circuit: Circuit = Circuit.model(),
    wikiUrl: String? = "wikiUrl",
    youtubeUrl: String? = "youtube"
): RaceData = RaceData(
    season = season,
    round = round,
    name = name,
    date = date,
    time = time,
    laps = laps,
    circuit = circuit,
    wikiUrl = wikiUrl,
    youtubeUrl = youtubeUrl
)