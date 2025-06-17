package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class OverviewRace(
    val date: LocalDate,
    val time: LocalTime?,
    val season: Int,
    val round: Int,
    val raceName: String,
    val circuitId: String,
    val circuitName: String,
    val laps: String?,
    val country: String,
    val countryISO: String,
    val hasQualifying: Boolean,
    val hasSprint: Boolean,
    val hasResults: Boolean,
    val schedule: List<Schedule>
) {
    companion object

    fun toRaceInfo(): RaceInfo {
        return RaceInfo(
            season = season,
            round = round,
            date = date,
            time = time,
            name = raceName,
            laps = laps,
            youtube = null,
            wikipediaUrl = null,
            circuit = Circuit(
                id = circuitId,
                name = circuitName,
                wikiUrl = null,
                city = "",
                country = country,
                countryISO = countryISO,
                location = null
            )
        )
    }
}