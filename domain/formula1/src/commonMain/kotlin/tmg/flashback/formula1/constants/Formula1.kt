package tmg.flashback.formula1.constants

import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object Formula1 {

    /**
     * Current Year season
     */
    val currentSeasonYear: Int by lazy {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    }

    /**
     * When did F1 start
     */
    const val championshipStarted = 1950

    /**
     * Qualifying data available from
     */
    const val qualifyingDataAvailableFrom: Int = 1995

    /**
     * When did championships start
     */
    const val championshipConstructorStarts = 1958

    /**
     * Sprint race began
     */
    const val sprintsIntroducedIn = 2021

    /**
     * Maximum points awarded to a driver in a single race based on the
     * season
     */
    fun maxDriverPointsBySeason(season: Int): Int {
        return when {
            season >= 2025 -> 25
            season >= 2010 -> 26
            season >= 1991 -> 11
            else -> 8
        }
    }
    fun maxTeamPointsBySeason(season: Int): Int {
        return when {
            season >= 2025 -> 58
            season >= 2022 -> 60
            season >= 2021 -> 47
            season >= 2010 -> 42
            season >= 1991 -> 19
            else -> 14
        }
    }

    val decadeColours: Map<String, String> = mapOf(
        "1950" to "#9FA8DA",
        "1960" to "#80DEEA",
        "1970" to "#EF9A9A",
        "1980" to "#90CAF9",
        "1990" to "#A5D6A7",
        "2000" to "#81D4FA",
        "2010" to "#CE93D8",
        "2020" to "#F48FB1",
        "2030" to "#B39DDB",
        "2040" to "#C5E1A5",
        "2050" to "#80CBC4",
        "2060" to "#B0BEC5",
        "2070" to "#FFCC80",
        "2080" to "#FFAB91",
        "2090" to "#B0BEC5"
    )
}
