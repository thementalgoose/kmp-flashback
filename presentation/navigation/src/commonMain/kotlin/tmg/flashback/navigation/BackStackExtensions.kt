package tmg.flashback.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

inline fun <reified T: NavKey> NavBackStack<NavKey>.replaceDetail(with: T) {
    this.removeDetail()
    this.add(with)
}

fun NavBackStack<NavKey>.removeDetail() {
    this.removeAll { key -> !key.isList() }
}

inline fun <reified T: NavKey> NavBackStack<NavKey>.replaceList(with: T) {
    this.clear()
    this.add(with)
}

fun NavKey.isList() = when (this) {
    is NavCalendar -> true
    is NavDriverStandings -> true
    is NavTeamStandings -> true
    is NavCircuits -> true
    is NavWeekend -> false
    is NavCircuit -> false
    is NavDriver -> false
    is NavTeam -> false
    is NavDriverComparison -> false
    is NavRss -> true
    is NavWebpage -> false
    is NavReactionGame -> true
    is NavSettings -> true
    is NavLineup -> true
    is NavSettingsDarkMode -> false
    is NavSettingsTheme -> false
    is NavSettingsLayout -> false
    is NavSettingsWeather -> false
    is NavSettingsRssConfigure -> false
    is NavSettingsInAppBrowser -> false
    is NavSettingsNotificationResults -> false
    is NavSettingsNotificationUpcoming -> false
    is NavSettingsWidgets -> false
    is NavSettingsPrivacy -> false
    is NavPrivacyPolicy -> false
    is NavAbout -> true
    else -> true
}