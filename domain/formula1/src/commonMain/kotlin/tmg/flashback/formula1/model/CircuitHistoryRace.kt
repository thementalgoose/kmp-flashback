package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class CircuitHistoryRace(
    val name: String,
    val season: Int,
    val round: Int,
    val wikiUrl: String?,
    val date: LocalDate,
    val time: LocalTime?,
    val preview: List<CircuitHistoryRaceResult>
) {
    companion object
}