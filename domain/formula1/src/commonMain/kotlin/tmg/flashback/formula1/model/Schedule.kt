package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Schedule(
    val label: String,
    val date: LocalDate,
    val time: LocalTime,
    val weather: ScheduleWeather?
) {

    val timestamp: Timestamp by lazy {
        Timestamp(date, time)
    }

    companion object
}