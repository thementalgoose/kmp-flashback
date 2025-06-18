package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

fun CircuitHistoryRace.Companion.model(
    name: String = "name",
    season: Int = 2020,
    round: Int = 1,
    wikiUrl: String? = "wikiUrl",
    date: LocalDate = LocalDate(1995, 10, 12),
    time: LocalTime? = LocalTime(12, 34),
    preview: List<CircuitHistoryRaceResult> = listOf(CircuitHistoryRaceResult.model())
): CircuitHistoryRace = CircuitHistoryRace(
    name = name,
    season = season,
    round = round,
    wikiUrl = wikiUrl,
    date = date,
    time = time,
    preview = preview
)