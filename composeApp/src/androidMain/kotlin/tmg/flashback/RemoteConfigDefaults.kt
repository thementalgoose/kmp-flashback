package tmg.flashback

import kotlinx.datetime.LocalDate
import tmg.flashback.infrastructure.datetime.now

object RemoteConfigDefaults {

    private val FIRST_SEASON = 1950

    val defaults = mapOf(
        "config_url" to "https://flashback.pages.dev",
        "data_provided" to "All data sourced with ♥ by the Flashback team",
        "default_year" to generateDefaultSeason(),
        "easteregg_snow" to false,
        "easteregg_summer" to false,
        "easteregg_ukraine" to false,
        "email" to "thementalgoose@gmail.com",
        "reaction_game" to false,
        "rss" to false,
        "rss_add_custom" to false,
        "soft_upgrade" to false,
        "supported_seasons" to generateSeasonArray()
    )

    private fun generateDefaultSeason(): Int = LocalDate.now().year

    private fun generateSeasonArray(): String {
        val allSeasons = (LocalDate.now().year) - FIRST_SEASON
        val seasons = List(allSeasons) { FIRST_SEASON + it }
        val seasonArray = seasons.joinToString(separator = ",") { """{"s":$it}"""}
        return """{"seasons":[${seasonArray}]}"""
    }
}