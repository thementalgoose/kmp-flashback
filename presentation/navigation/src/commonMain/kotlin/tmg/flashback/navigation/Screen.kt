package tmg.flashback.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object NavCalendar: NavKey

@Serializable
data object NavDriverStandings: NavKey

@Serializable
data object NavTeamStandings: NavKey

@Serializable
data object NavCircuits: NavKey

@Serializable
data class NavWeekend(
    val season: Int,
    val round: Int,
    val raceName: String,
): NavKey

@Serializable
data class NavCircuit(
    val id: String,
    val name: String,
): NavKey {
    companion object
}

@Serializable
data object NavLineup: NavKey

@Serializable
data class NavDriver(
    val season: Int,
    val id: String,
    val name: String
): NavKey

@Serializable
data class NavTeam(
    val season: Int,
    val id: String,
    val name: String
): NavKey

@Serializable
data class NavDriverComparison(
    val season: Int
): NavKey

@Serializable
data object NavRss: NavKey

@Serializable
data class NavWebpage(
    val url: String
): NavKey

@Serializable
data object NavReactionGame: NavKey

@Serializable
data object NavGlossary: NavKey

@Serializable
data class NavGlossaryDetail(
    val id: String
): NavKey

@Serializable
data object NavSettings: NavKey

@Serializable
data object NavSettingsDarkMode: NavKey

@Serializable
data object NavSettingsTheme: NavKey

@Serializable
data object NavSettingsLayout: NavKey

@Serializable
data object NavSettingsWeather: NavKey

@Serializable
data object NavSettingsRssConfigure: NavKey

@Serializable
data object NavSettingsInAppBrowser: NavKey

@Serializable
data object NavSettingsNotificationResults: NavKey

@Serializable
data object NavSettingsNotificationUpcoming: NavKey

@Serializable
data object NavSettingsWidgets: NavKey

@Serializable
data object NavSettingsPrivacy: NavKey

@Serializable
data object NavPrivacyPolicy: NavKey

@Serializable
data object NavAbout: NavKey