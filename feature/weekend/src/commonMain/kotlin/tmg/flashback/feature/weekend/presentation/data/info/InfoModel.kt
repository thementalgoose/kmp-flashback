package tmg.flashback.feature.weekend.presentation.data.info

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Schedule
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
    val cancelled: Boolean,
    val youtubeUrl: String?,
    val aerialUrl: String?,
    val wikipediaUrl: String?,
    val days: List<Pair<LocalDate, List<Pair<Schedule, Boolean>>>>,
    val temperatureMetric: Boolean,
    val windspeedMetric: Boolean
) {
    val isUpcoming by lazy {
        LocalDate.now() <= date
    }

    companion object
}

private fun LocalTime.adjustToUTC(): LocalTime {
    return this.plusMinutes(-60)
}

fun InfoModel.Companion.preview(): InfoModel {
    val nowDate = LocalDate.now()
    val nowTime = LocalTime.now()
    return InfoModel(
        season = 2020,
        round = 1,
        raceName = "British Grand Prix",
        date = nowDate.plus(1, DateTimeUnit.DAY),
        time = nowTime.adjustToUTC(),
        circuit = Circuit.preview(),
        laps = "100",
        cancelled = false,
        youtubeUrl = "youtube",
        wikipediaUrl = "wiki",
        days = listOf(
            nowDate.minus(1, DateTimeUnit.DAY) to listOf(
                Schedule.preview(
                    label = "FP1",
                    date = nowDate.minus(1, DateTimeUnit.DAY),
                    time = nowTime.plusMinutes(-120).adjustToUTC()
                ) to false,
                Schedule.preview(
                    label = "FP2",
                    date = nowDate.minus(1, DateTimeUnit.DAY),
                    time = nowTime.adjustToUTC()
                ) to false
            ),
            nowDate to listOf(
                Schedule.preview(
                    label = "FP3",
                    time = nowTime.plusMinutes(-30).adjustToUTC()
                ) to false,
                Schedule.preview(
                    label = "Qua",
                    time = nowTime.plusMinutes(20).adjustToUTC()
                ) to false
            ),
            nowDate.plus(1, DateTimeUnit.DAY) to listOf(
                Schedule.preview(
                    label = "Rac",
                    date = nowDate.plus(1, DateTimeUnit.DAY),
                    time = nowTime.adjustToUTC()
                ) to false,
            )
        ),
        aerialUrl = null,
        temperatureMetric = true,
        windspeedMetric = true
    )
}