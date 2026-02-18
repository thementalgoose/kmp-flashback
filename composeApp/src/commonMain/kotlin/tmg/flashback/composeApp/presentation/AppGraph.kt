package tmg.flashback.composeApp.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.logo
import tmg.flashback.feature.about.presentation.AboutScreen
import tmg.flashback.feature.privacypolicy.presentation.PrivacyPolicyScreen
import tmg.flashback.feature.reactiongame.presentation.ReactionGameScreen
import tmg.flashback.feature.rss.presentation.feed.RssFeedGraph
import tmg.flashback.navigation.Screen
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
import tmg.flashback.feature.circuits.presentation.all.AllCircuitsScreen
import tmg.flashback.feature.circuits.presentation.circuit.CircuitScreen
import tmg.flashback.feature.constructors.presentation.stats.ConstructorStatsScreen
import tmg.flashback.feature.drivers.presentation.comparison.DriverComparisonScreen
import tmg.flashback.feature.drivers.presentation.stats.DriverStatsScreen
import tmg.flashback.feature.rss.presentation.configure.RssConfigureScreen
import tmg.flashback.feature.rss.presentation.feed.RSSScreen
import tmg.flashback.feature.season.presentation.calendar.CalendarScreen
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsScreen
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsScreen
import tmg.flashback.feature.weekend.presentation.WeekendScreen
import tmg.flashback.navigation.ListDetailScene
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
            entry<Screen.Calendar>(metadata = ListDetailScene.listPane()) {
                CalendarScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<Screen.DriverStandings>(metadata = ListDetailScene.listPane()) {
                DriverStandingsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<Screen.TeamStandings>(metadata = ListDetailScene.listPane()) {
                TeamStandingsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<Screen.Circuit>(metadata = ListDetailScene.listPane()) {
                AllCircuitsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }


            entry<Screen.Weekend>(metadata = ListDetailScene.detailPane()) {
                WeekendScreen(
                    data = it,
                    paddingValues = insetPadding,
                    showBack = true,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<Screen.Circuit>(metadata = ListDetailScene.detailPane()) {
                CircuitScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    showBack = true,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<Screen.Driver>(metadata = ListDetailScene.detailPane()) {
                DriverStatsScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    showBack = true,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }
            entry<Screen.Team>(metadata = ListDetailScene.detailPane()) {
                ConstructorStatsScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    showBack = true,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<Screen.DriverComparison>(metadata = ListDetailScene.detailPane()) {
                DriverComparisonScreen(
                    season = it.season,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }

            entry<Screen.Rss>(metadata = ListDetailScene.listPane()) {
                RSSScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.add(it)
                    }
                )
            }
            entry<Screen.Webpage>(metadata = ListDetailScene.detailPane()) {
                WebScreen(
                    url = it.url,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeLastOrNull() },
                    shareClicked = { },
                    openInBrowser = { },
                    toolbarAtTop = true
                )
            }

            entry<Screen.ReactionGame>(metadata = ListDetailScene.listPane()) {
                ReactionGameScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<Screen.Settings>(metadata = ListDetailScene.listPane()) {
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
            entry<Screen.SettingsDarkMode>(metadata = ListDetailScene.detailPane()) {
                SettingsDarkModeScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsTheme>(metadata = ListDetailScene.detailPane()) {
                SettingsThemeScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsLayout>(metadata = ListDetailScene.detailPane()) {
                SettingsLayoutScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsWeather>(metadata = ListDetailScene.detailPane()) {
                SettingsWeatherScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsRssConfigure>(metadata = ListDetailScene.detailPane()) {
                RssConfigureScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsInAppBrowser>(metadata = ListDetailScene.detailPane()) {
                SettingsBrowserScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsNotificationResults>(metadata = ListDetailScene.detailPane()) {
                SettingsNotificationResultsScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsNotificationUpcoming>(metadata = ListDetailScene.detailPane()) {
                SettingsNotificationUpcomingScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsWidgets>(metadata = ListDetailScene.detailPane()) {
                SettingsWidgetScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.SettingsPrivacy>(metadata = ListDetailScene.detailPane()) {
                SettingsPrivacyScreen(
                    actionUpClicked = { backStack.removeLastOrNull() },
                    insetPadding = insetPadding,
                    showBack = true
                )
            }
            entry<Screen.PrivacyPolicy>(metadata = ListDetailScene.detailPane()) {
                PrivacyPolicyScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel
                )
            }

            entry<Screen.About>(metadata = ListDetailScene.listPane()) {
                AboutScreen(
                    appIcon = Res.drawable.logo,
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Calendar,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        modifier = modifier,
    ) {
        composable<Screen.Rss> {
            RssFeedGraph(
                paddingValues = insetPadding,
                actionUpClicked = openPanel,
                navigator = rssNavigator,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
    }
}