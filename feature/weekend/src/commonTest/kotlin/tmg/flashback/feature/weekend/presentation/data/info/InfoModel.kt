package tmg.flashback.feature.weekend.presentation.data.info

import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.model

fun InfoModel.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    raceName: String = "name",
    circuit: Circuit = Circuit.model(),
    laps: String? = "12",
    youtubeUrl: String? = "youtube",
    wikipediaUrl: String? = "wikiUrl",
    schedule: List<Schedule> = listOf(Schedule.model()),
): InfoModel = InfoModel(
    season = season,
    round = round,
    raceName = raceName,
    circuit = circuit,
    laps = laps,
    youtubeUrl = youtubeUrl,
    wikipediaUrl = wikipediaUrl,
    schedule = schedule
)