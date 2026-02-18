package tmg.flashback.composeApp.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.logo
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.settings.AllSettingsScreen
import tmg.flashback.composeApp.presentation.settings.browser.SettingsBrowserScreen
import tmg.flashback.composeApp.presentation.settings.darkmode.SettingsDarkModeScreen
import tmg.flashback.composeApp.presentation.settings.layout.SettingsLayoutScreen
import tmg.flashback.composeApp.presentation.settings.notifications.results.SettingsNotificationResultsScreen
import tmg.flashback.composeApp.presentation.settings.notifications.upcoming.SettingsNotificationUpcomingScreen
import tmg.flashback.composeApp.presentation.settings.privacy.SettingsPrivacyScreen
import tmg.flashback.composeApp.presentation.settings.theme.SettingsThemeScreen
import tmg.flashback.composeApp.presentation.settings.weather.SettingsWeatherScreen
import tmg.flashback.composeApp.presentation.settings.widgets.SettingsWidgetScreen
import tmg.flashback.feature.about.presentation.AboutScreen
import tmg.flashback.feature.circuits.presentation.all.AllCircuitsScreen
import tmg.flashback.feature.circuits.presentation.circuit.CircuitScreen
import tmg.flashback.feature.constructors.presentation.stats.ConstructorStatsScreen
import tmg.flashback.feature.drivers.presentation.comparison.DriverComparisonScreen
import tmg.flashback.feature.drivers.presentation.stats.DriverStatsScreen
import tmg.flashback.feature.privacypolicy.presentation.PrivacyPolicyScreen
import tmg.flashback.feature.reactiongame.presentation.ReactionGameScreen
import tmg.flashback.feature.rss.presentation.configure.RssConfigureScreen
import tmg.flashback.feature.rss.presentation.feed.RSSScreen
import tmg.flashback.feature.season.presentation.calendar.CalendarScreen
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsScreen
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsScreen
import tmg.flashback.feature.weekend.presentation.WeekendScreen
import tmg.flashback.navigation.ListDetailScene
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuit
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriver
import tmg.flashback.navigation.NavDriverComparison
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavPrivacyPolicy
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavSettingsDarkMode
import tmg.flashback.navigation.NavSettingsInAppBrowser
import tmg.flashback.navigation.NavSettingsLayout
import tmg.flashback.navigation.NavSettingsNotificationResults
import tmg.flashback.navigation.NavSettingsNotificationUpcoming
import tmg.flashback.navigation.NavSettingsPrivacy
import tmg.flashback.navigation.NavSettingsRssConfigure
import tmg.flashback.navigation.NavSettingsTheme
import tmg.flashback.navigation.NavSettingsWeather
import tmg.flashback.navigation.NavSettingsWidgets
import tmg.flashback.navigation.NavTeam
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.NavWebpage
import tmg.flashback.navigation.NavWeekend
import tmg.flashback.navigation.rememberListDetailSceneStrategy
import tmg.flashback.webbrowser.presentation.WebScreen

@Composable
fun AppGraph(
    openPanel: () -> Unit,
    appNavigationViewModel: AppNavigationViewModel,
    insetPadding: PaddingValues,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
) {
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        sceneStrategy = listDetailStrategy,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<NavCalendar>(metadata = ListDetailScene.listPane()) {
                CalendarScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<NavDriverStandings>(metadata = ListDetailScene.listPane()) {
                DriverStandingsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<NavTeamStandings>(metadata = ListDetailScene.listPane()) {
                TeamStandingsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<NavCircuits>(metadata = ListDetailScene.listPane()) {
                AllCircuitsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }

            entry<NavWeekend>(metadata = ListDetailScene.detailPane()) {
                WeekendScreen(
                    data = it,
                    paddingValues = insetPadding,
                    showBack = true,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavCircuit>(metadata = ListDetailScene.detailPane()) {
                CircuitScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    showBack = true,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavDriver>(metadata = ListDetailScene.detailPane()) {
                DriverStatsScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    showBack = true,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }
            entry<NavTeam>(metadata = ListDetailScene.detailPane()) {
                ConstructorStatsScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    showBack = true,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavDriverComparison>(metadata = ListDetailScene.detailPane()) {
                DriverComparisonScreen(
                    season = it.season,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }

            entry<NavRss>(metadata = ListDetailScene.listPane()) {
                RSSScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<NavWebpage>(metadata = ListDetailScene.detailPane()) {
                WebScreen(
                    url = it.url,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    shareClicked = { },
                    openInBrowser = { },
                    toolbarAtTop = true
                )
            }

            entry<NavReactionGame>(metadata = ListDetailScene.listPane()) {
                ReactionGameScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<NavSettings>(metadata = ListDetailScene.listPane()) {
                AllSettingsScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    showMenu = true,
                    navigateToSubScreen = { backStack.add(it) },
                    navigateTo = {
                        backStack.clear()
                        backStack.add(it)
                    },
                    insetPadding = insetPadding
                )
            }
            entry<NavSettingsDarkMode>(metadata = ListDetailScene.detailPane()) {
                SettingsDarkModeScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsTheme>(metadata = ListDetailScene.detailPane()) {
                SettingsThemeScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsLayout>(metadata = ListDetailScene.detailPane()) {
                SettingsLayoutScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsWeather>(metadata = ListDetailScene.detailPane()) {
                SettingsWeatherScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsRssConfigure>(metadata = ListDetailScene.detailPane()) {
                RssConfigureScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsInAppBrowser>(metadata = ListDetailScene.detailPane()) {
                SettingsBrowserScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsNotificationResults>(metadata = ListDetailScene.detailPane()) {
                SettingsNotificationResultsScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsNotificationUpcoming>(metadata = ListDetailScene.detailPane()) {
                SettingsNotificationUpcomingScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsWidgets>(metadata = ListDetailScene.detailPane()) {
                SettingsWidgetScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavSettingsPrivacy>(metadata = ListDetailScene.detailPane()) {
                SettingsPrivacyScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<NavPrivacyPolicy>(metadata = ListDetailScene.detailPane()) {
                PrivacyPolicyScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel
                )
            }

            entry<NavAbout>(metadata = ListDetailScene.listPane()) {
                AboutScreen(
                    appIcon = Res.drawable.logo,
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
        }
    )
}