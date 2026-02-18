package tmg.flashback.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen: NavKey {

    @Serializable
    data object Calendar: Screen

    @Serializable
    data object DriverStandings: Screen

    @Serializable
    data object TeamStandings: Screen

    @Serializable
    data object Circuits: Screen

    @Serializable
    data class Weekend(
        val season: Int,
        val round: Int,
        val raceName: String,
    ): Screen

    @Serializable
    data class Circuit(
        val id: String,
        val name: String,
    ): Screen {
        companion object
    }

    @Serializable
    data class Driver(
        val season: Int,
        val id: String,
        val name: String
    ): Screen

    @Serializable
    data class Team(
        val season: Int,
        val id: String,
        val name: String
    ): Screen

    @Serializable
    data class DriverComparison(
        val season: Int
    ): Screen

    @Serializable
    data object Rss: Screen

    @Serializable
    data class Webpage(
        val url: String
    ): Screen

    @Serializable
    data object ReactionGame: Screen

    @Serializable
    data object Settings: Screen

    @Serializable
    data object SettingsDarkMode: Screen

    @Serializable
    data object SettingsTheme: Screen

    @Serializable
    data object SettingsLayout: Screen

    @Serializable
    data object SettingsWeather: Screen

    @Serializable
    data object SettingsRssConfigure: Screen

    @Serializable
    data object SettingsInAppBrowser: Screen

    @Serializable
    data object SettingsNotificationResults: Screen

    @Serializable
    data object SettingsNotificationUpcoming: Screen

    @Serializable
    data object SettingsWidgets: Screen

    @Serializable
    data object SettingsPrivacy: Screen

    @Serializable
    data object PrivacyPolicy: Screen

    @Serializable
    data object About: Screen
}