package tmg.flashback.feature.weekend.presentation.data.info

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.ScheduleWeather
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.plusMinutes

data class InfoModel(
    val season: Int,
    val round: Int,
    val raceName: String,
    val date: LocalDate,
    val time: LocalTime?,
    val circuit: Circuit,
    val laps: String?,
    val trackLayout: TrackLayout?,
    val cancelled: Boolean,
    val youtubeUrl: String?,
    val aerialUrl: String?,
    val wikipediaUrl: String?,
    val days: List<Pair<LocalDate, List<Pair<Schedule, Boolean>>>>,
    val temperatureMetric: Boolean,
    val windspeedMetric: Boolean,
    val showWeatherDetails: Boolean,
) {
    val isUpcoming by lazy {
        LocalDate.now() <= date
    }

    companion object
}

private fun LocalTime.adjustToUTC(): LocalTime {
    return this.plusMinutes(-60)
}

fun InfoModel.Companion.preview(
    weather: ScheduleWeather? = null,
    trackLayout: TrackLayout? = TrackLayout.SILVERSTONE,
    circuit: Circuit = Circuit.preview(),
): InfoModel {
    val nowDate = LocalDate.now()
    val nowTime = LocalTime.now()
    return InfoModel(
        season = 2020,
        round = 1,
        raceName = "British Grand Prix",
        date = nowDate.plus(1, DateTimeUnit.DAY),
        time = nowTime.adjustToUTC(),
        circuit = circuit,
        laps = "100",
        cancelled = false,
        youtubeUrl = "youtube",
        wikipediaUrl = "wiki",
        days = listOf(
            nowDate.minus(1, DateTimeUnit.DAY) to listOf(
                Schedule.preview(
                    label = "FP1",
                    date = nowDate.minus(1, DateTimeUnit.DAY),
                    time = nowTime.plusMinutes(-120).adjustToUTC(),
                    weather = weather,
                ) to false,
                Schedule.preview(
                    label = "FP2",
                    date = nowDate.minus(1, DateTimeUnit.DAY),
                    time = nowTime.adjustToUTC(),
                    weather = weather,
                ) to false
            ),
            nowDate to listOf(
                Schedule.preview(
                    label = "FP3",
                    time = nowTime.plusMinutes(-30).adjustToUTC(),
                    weather = weather,
                ) to false,
                Schedule.preview(
                    label = "Qua",
                    time = nowTime.plusMinutes(20).adjustToUTC(),
                    weather = weather,
                ) to false
            ),
            nowDate.plus(1, DateTimeUnit.DAY) to listOf(
                Schedule.preview(
                    label = "Rac",
                    date = nowDate.plus(1, DateTimeUnit.DAY),
                    time = nowTime.adjustToUTC(),
                    weather = weather,
                ) to false,
            )
        ),
        trackLayout = trackLayout,
        aerialUrl = null,
        temperatureMetric = true,
        windspeedMetric = true,
        showWeatherDetails = true
    )
}