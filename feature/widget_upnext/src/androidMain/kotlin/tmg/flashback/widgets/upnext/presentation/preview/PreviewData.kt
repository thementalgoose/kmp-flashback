package tmg.flashback.widgets.upnext.presentation.preview

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.plusMinutes

val fakeOverviewRace = OverviewRace(
    date = LocalDate(2020, 1, 22),
    time = LocalTime(15, 0, 0),
    season = 2020,
    round = 1,
    raceName = "Emilia Romagna Grand Prix",
    circuitId = "imola",
    circuitName = "Imola Circuit",
    laps = "66",
    country = "Italy",
    countryISO = "ITA",
    hasQualifying = false,
    hasSprint = false,
    hasResults = false,
    cancelled = false,
    schedule = relativeSchedule(isSprint = false)
)

val fakeSprintWeekend = OverviewRace(
    date = LocalDate(2020, 1, 22),
    time = LocalTime(15, 0, 0),
    season = 2020,
    round = 1,
    raceName = "Emilia Romagna Grand Prix",
    circuitId = "imola",
    circuitName = "Imola Circuit",
    laps = "66",
    country = "Italy",
    countryISO = "ITA",
    hasQualifying = false,
    hasSprint = false,
    hasResults = false,
    cancelled = false,
    schedule = relativeSchedule(isSprint = true)
)

private fun relativeSchedule(
    isSprint: Boolean = false
): List<Schedule> {
    return when (isSprint) {
        true -> listOf(
            Schedule("FP1", LocalDate.now().minus(1, DateTimeUnit.DAY), LocalTime.now().plusMinutes(-120), null),
            Schedule("Sprint Qualifying", LocalDate.now().minus(1, DateTimeUnit.DAY), LocalTime.now().plusMinutes(1), null),
            Schedule("Sprint", LocalDate.now(), LocalTime.now().plusMinutes(-120), null),
            Schedule("Qualifying", LocalDate.now(), LocalTime.now().plusMinutes(1), null),
            Schedule("Race", LocalDate.now().plus(1, DateTimeUnit.DAY), LocalTime.now().plusMinutes(1), null)
        )
        false -> listOf(
            Schedule("FP1", LocalDate.now().minus(1, DateTimeUnit.DAY), LocalTime.now().plusMinutes(-120), null),
            Schedule("FP2", LocalDate.now().minus(1, DateTimeUnit.DAY), LocalTime.now().plusMinutes(1), null),
            Schedule("FP3", LocalDate.now(), LocalTime.now().plusMinutes(-120), null),
            Schedule("Qualifying", LocalDate.now(), LocalTime.now().plusMinutes(1), null),
            Schedule("Race", LocalDate.now().plus(1, DateTimeUnit.DAY), LocalTime.now().plusMinutes(1), null)
        )
    }
}