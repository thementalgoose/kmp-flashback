package tmg.flashback.feature.weekend.presentation.data.info

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.model

fun InfoModel.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    raceName: String = "name",
    circuit: Circuit = Circuit.model(),
    laps: String? = "12",
    date: LocalDate = LocalDate(2020, 10, 12),
    time: LocalTime? = LocalTime(12, 34),
    youtubeUrl: String? = "youtube",
    wikipediaUrl: String? = "wikiUrl",
    temperatureMetric: Boolean = true,
    windspeedMetric: Boolean = true
): InfoModel = InfoModel(
    season = season,
    round = round,
    raceName = raceName,
    circuit = circuit,
    laps = laps,
    youtubeUrl = youtubeUrl,
    wikipediaUrl = wikipediaUrl,
    days = listOf(
        LocalDate(2020, 1, 1) to listOf(
            Schedule.model() to false
        )
    ),
    date = date,
    time = time,
    temperatureMetric = temperatureMetric,
    windspeedMetric = windspeedMetric
)