package tmg.flashback.widgets.upnext.presentation.preview

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule

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
    schedule = listOf(
        Schedule("FP1", LocalDate(2020, 1, 1), LocalTime(12, 0), null),
        Schedule("FP2", LocalDate(2020, 1, 1), LocalTime(15, 0), null),
        Schedule("FP3", LocalDate(2020, 1, 2), LocalTime(11, 0), null),
        Schedule("Qualifying", LocalDate(2020, 1, 2), LocalTime(14, 0), null),
        Schedule("Race", LocalDate(2020, 1, 3), LocalTime(15, 0), null)
    )
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
    schedule = listOf(
        Schedule("FP1", LocalDate(2020, 1, 1), LocalTime(12, 0), null),
        Schedule("Sprint Qualifying", LocalDate(2020, 1, 1), LocalTime(15, 0), null),
        Schedule("Sprint", LocalDate(2020, 1, 2), LocalTime(11, 0), null),
        Schedule("Qualifying", LocalDate(2020, 1, 2), LocalTime(14, 0), null),
        Schedule("Race", LocalDate(2020, 1, 3), LocalTime(15, 0), null)
    )
)