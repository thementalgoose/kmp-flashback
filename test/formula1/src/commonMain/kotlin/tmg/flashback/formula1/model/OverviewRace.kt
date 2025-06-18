package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

fun OverviewRace.Companion.model(
    date: LocalDate = LocalDate(2020, 10, 12),
    time: LocalTime? = LocalTime(12, 34, 0),
    season: Int = 2020,
    round: Int = 1,
    raceName: String = "name",
    circuitId: String = "circuitId",
    circuitName: String = "circuitName",
    country: String = "country",
    countryISO: String = "countryISO",
    laps: String? = "12",
    hasQualifying: Boolean = true,
    hasSprint: Boolean = false,
    hasResults: Boolean = false,
    schedule: List<Schedule> = listOf(
        Schedule.model()
    )
): OverviewRace = OverviewRace(
    date = date,
    time = time,
    season = season,
    round = round,
    raceName = raceName,
    circuitId = circuitId,
    circuitName = circuitName,
    laps = laps,
    country = country,
    countryISO = countryISO,
    hasQualifying = hasQualifying,
    hasSprint = hasSprint,
    hasResults = hasResults,
    schedule = schedule
)