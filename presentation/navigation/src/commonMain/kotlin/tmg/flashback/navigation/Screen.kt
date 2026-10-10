package tmg.flashback.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey

@Serializable
data object NavCalendar: Screen

@Serializable
data object NavDriverStandings: Screen

@Serializable
data object NavTeamStandings: Screen

@Serializable
data object NavCircuits: Screen

@Serializable
data class NavWeekend(
    val season: Int,
    val round: Int,
    val raceName: String,
    val defaultTab: String? = null,
): Screen

@Serializable
data class NavCircuit(
    val id: String,
    val name: String,
): Screen {
    companion object
}

@Serializable
data object NavLineup: Screen

@Serializable
data class NavDriver(
    val season: Int,
    val id: String,
    val name: String
): Screen

@Serializable
data class NavTeam(
    val season: Int,
    val id: String,
    val name: String
): Screen

@Serializable
data class NavDriverComparison(
    val season: Int
): Screen

@Serializable
data object NavRss: Screen

@Serializable
data class NavWebpage(
    val url: String
): Screen

@Serializable
data object NavReactionGame: Screen

@Serializable
data object NavGlossary: Screen

@Serializable
data class NavGlossaryDetail(
    val id: String
): Screen

@Serializable
data object NavSettings: Screen

@Serializable
data object NavSettingsDarkMode: Screen

@Serializable
data object NavSettingsTheme: Screen

@Serializable
data object NavSettingsLayoutHome: Screen

@Serializable
data object NavSettingsLayoutRace: Screen

@Serializable
data object NavSettingsRssConfigure: Screen

@Serializable
data object NavSettingsInAppBrowser: Screen

@Serializable
data object NavSettingsNotificationResults: Screen

@Serializable
data object NavSettingsNotificationUpcoming: Screen

@Serializable
data object NavSettingsWidgets: Screen

@Serializable
data object NavSettingsPrivacy: Screen

@Serializable
data object NavPrivacyPolicy: Screen

@Serializable
data object NavAbout: Screen

@Serializable
data object NavStyleGuide: Screen