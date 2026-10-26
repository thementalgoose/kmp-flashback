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
data object NavPaddock: Screen

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
    val season: Int?,
    val id: String,
    val name: String
): Screen

@Serializable
data class NavTeam(
    val season: Int?,
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
data class NavSearch(
    val filter: NavSearchFilter? = null,
): Screen

@Serializable
enum class NavSearchFilter {
    Drivers,
    Teams,
    Circuits
//    Races;
}

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

@Serializable
data object NavInfo: Screen

fun Screen.isList(): Boolean = when (this) {
    is NavCalendar -> true
    is NavDriverStandings -> true
    is NavTeamStandings -> true
    is NavPaddock -> true
    is NavSettings -> true

    is NavRss -> true
    is NavReactionGame -> true
    is NavSearch -> true

    is NavLineup -> true
    is NavGlossary -> true
    is NavStyleGuide -> true
    is NavInfo -> true

    is NavAbout -> false
    is NavWeekend -> false
    is NavCircuit -> false
    is NavDriver -> false
    is NavTeam -> false
    is NavDriverComparison -> false
    is NavWebpage -> false
    is NavGlossaryDetail -> false
    is NavSettingsDarkMode -> false
    is NavSettingsTheme -> false
    is NavSettingsLayoutHome -> false
    is NavSettingsLayoutRace -> false
    is NavSettingsRssConfigure -> false
    is NavSettingsInAppBrowser -> false
    is NavSettingsNotificationResults -> false
    is NavSettingsNotificationUpcoming -> false
    is NavSettingsWidgets -> false
    is NavSettingsPrivacy -> false
    is NavPrivacyPolicy -> false
}

fun Screen.requiresPaddock(): Boolean = when (this) {
    is NavRss,
    is NavReactionGame,
    is NavSearch -> true
    else -> false
}

fun Screen.requiresSettings(): Boolean = when (this) {
    is NavAbout -> true
    else -> false
}

