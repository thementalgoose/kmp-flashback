package tmg.flashback.feature.weekend.presentation.data.info

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.preview.preview

data class InfoModel(
    val season: Int,
    val round: Int,
    val raceName: String,
    val date: LocalDate,
    val time: LocalTime?,
    val circuit: Circuit,
    val laps: String?,
    val youtubeUrl: String?,
    val wikipediaUrl: String?,
    val days: List<Pair<LocalDate, List<Pair<Schedule, Boolean>>>>,
    val temperatureMetric: Boolean,
    val windspeedMetric: Boolean
) {
    companion object
}

fun InfoModel.Companion.preview() = InfoModel(
    season = 2020,
    round = 1,
    raceName = "Apex Grand Prix",
    date = LocalDate(2020, 1, 1),
    time = LocalTime(12, 0),
    circuit = Circuit.preview(),
    laps = "100",
    youtubeUrl = "youtube",
    wikipediaUrl = "wiki",
    days = listOf(
        LocalDate(2020, 1, 1) to listOf(
            Schedule.preview("FP1") to false,
            Schedule.preview("FP2") to false
        ),
        LocalDate(2020, 1, 2) to listOf(
            Schedule.preview("FP3") to false,
            Schedule.preview("Qualifying") to false
        ),
        LocalDate(2020, 1, 3) to listOf(
            Schedule.preview("Race") to false,
        )
    ),
    temperatureMetric = true,
    windspeedMetric = true
)