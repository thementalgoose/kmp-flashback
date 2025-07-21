package tmg.flashback.presentation

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
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.presentation.settings.AllSettingsGraph
import tmg.flashback.presentation.settings.SettingNavigation
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.navigation.MasterDetailPaneState

@Composable
fun AppGraph(
    openPanel: () -> Unit,
    appNavigationViewModel: AppNavigationViewModel,
    navController: NavHostController,
    insetPadding: PaddingValues,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    calendarNavigator: MasterDetailPaneState<WeekendNavigation>,
    driverStandingsNavigator: MasterDetailPaneState<DriverStandingsNavigation>,
    teamStandingsNavigator: MasterDetailPaneState<TeamStandingsNavigation>,
    rssNavigator: MasterDetailPaneState<RssNavigation>,
    settingsNavigator: MasterDetailPaneState<SettingNavigation>,
    circuitsNavigator: MasterDetailPaneState<CircuitNavigation>,
    modifier: Modifier = Modifier,
) {
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
        composable<Screen.ReactionGame> {
            ReactionGameScreen(
                paddingValues = insetPadding,
                actionUpClicked = openPanel,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
        composable<Screen.PrivacyPolicy> {
            PrivacyPolicyScreen(
                paddingValues = insetPadding,
                actionUpClicked = openPanel
            )
        }
        composable<Screen.About> {
            AboutScreen(
                appIcon = Res.drawable.logo,
                paddingValues = insetPadding,
                actionUpClicked = openPanel,
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
        composable<Screen.Driver> {
            Box(Modifier.fillMaxSize()) {
                TextTitle("Driver", Modifier.align(Alignment.Center))
            }
        }
        composable<Screen.Constructor> {
            Box(Modifier.fillMaxSize()) {
                TextTitle("Constructor", Modifier.align(Alignment.Center))
            }
        }
        composable<Screen.Circuit> {
            Box(Modifier.fillMaxSize()) {
                TextTitle("Circuit", Modifier.align(Alignment.Center))
            }
        }
    }
}