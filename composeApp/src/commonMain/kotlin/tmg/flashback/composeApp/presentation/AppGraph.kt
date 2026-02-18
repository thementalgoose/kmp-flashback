package tmg.flashback.composeApp.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.logo
import tmg.flashback.feature.about.presentation.AboutScreen
import tmg.flashback.feature.circuits.presentation.all.CircuitsGraph
import tmg.flashback.feature.circuits.presentation.all.AllCircuitsScreen
import tmg.flashback.feature.circuits.presentation.all.CircuitNavigation
import tmg.flashback.feature.privacypolicy.presentation.PrivacyPolicyScreen
import tmg.flashback.feature.reactiongame.presentation.ReactionGameScreen
import tmg.flashback.feature.rss.presentation.feed.RssNavigation
import tmg.flashback.feature.rss.presentation.feed.RssFeedGraph
import tmg.flashback.feature.season.presentation.calendar.CalendarGraph
import tmg.flashback.feature.season.presentation.calendar.WeekendNavigation
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsGraph
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsNavigation
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsGraph
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsNavigation
import tmg.flashback.navigation.Screen
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.settings.AllSettingsGraph
import tmg.flashback.composeApp.presentation.settings.SettingNavigation
import tmg.flashback.navigation.ListDetailScene
import tmg.flashback.navigation.rememberListDetailSceneStrategy
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.navigation.MasterDetailPaneState

@Composable
fun AppGraph(
    openPanel: () -> Unit,
    appNavigationViewModel: AppNavigationViewModel,
    navController: NavHostController,
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

            }
            entry<Screen.DriverStandings>(metadata = ListDetailScene.listPane()) {

            }
            entry<Screen.TeamStandings>(metadata = ListDetailScene.listPane()) {

            }
            entry<Screen.Circuit>(metadata = ListDetailScene.listPane()) {

            }


            entry<Screen.Weekend>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.Circuit>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.Driver>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.Team>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.DriverComparison>(metadata = ListDetailScene.detailPane()) {

            }

            entry<Screen.Rss>(metadata = ListDetailScene.listPane()) {

            }
            entry<Screen.Webpage>(metadata = ListDetailScene.detailPane()) {

            }

            entry<Screen.ReactionGame>(metadata = ListDetailScene.listPane()) {
                ReactionGameScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<Screen.Settings>(metadata = ListDetailScene.listPane()) {

            }
            entry<Screen.SettingsDarkMode>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsTheme>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsLayout>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsWeather>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsRssConfigure>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsInAppBrowser>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsNotificationResults>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsNotificationUpcoming>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsWidgets>(metadata = ListDetailScene.detailPane()) {

            }
            entry<Screen.SettingsPrivacy>(metadata = ListDetailScene.detailPane()) {

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
        composable<Screen.Calendar> {
            CalendarGraph(
                navigator = calendarNavigator,
                paddingValues = insetPadding,
                actionUpClicked = openPanel,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
        composable<Screen.DriverStandings> {
            DriverStandingsGraph(
                navigator = driverStandingsNavigator,
                paddingValues = insetPadding,
                actionUpClicked = openPanel,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass,
            )
        }
        composable<Screen.TeamStandings> {
            TeamStandingsGraph(
                navigator = teamStandingsNavigator,
                paddingValues = insetPadding,
                actionUpClicked = openPanel,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass,
            )
        }
        composable<Screen.Circuits> {
            CircuitsGraph(
                paddingValues = insetPadding,
                navigator = circuitsNavigator,
                actionUpClicked = openPanel,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
        composable<Screen.Rss> {
            RssFeedGraph(
                paddingValues = insetPadding,
                actionUpClicked = openPanel,
                navigator = rssNavigator,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
        composable<Screen.Settings> {
            AllSettingsGraph(
                windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                actionUpClicked = openPanel,
                navigator = settingsNavigator,
                insetPadding = insetPadding,
                navigateToAboutThisApp = {
                    navController.navigate(Screen.About)
                },
                navigateToPrivacyPolicy = {
                    navController.navigate(Screen.PrivacyPolicy)
                }
            )
        }
    }
}