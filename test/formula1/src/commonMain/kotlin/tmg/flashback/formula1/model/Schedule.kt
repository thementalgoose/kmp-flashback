package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

fun Schedule.Companion.model(
    label: String = "label",
    date: LocalDate = LocalDate(2020, 1, 1),
    time: LocalTime = LocalTime(12, 34),
    weather: ScheduleWeather = ScheduleWeather.model()
): Schedule = Schedule(
    label = label,
    date = date,
    time = time,
    weather = weather
)