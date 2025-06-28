package tmg.flashback.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tmg.flashback.feature.rss.presentation.feed.RssFeedGraph
import tmg.flashback.feature.season.presentation.calendar.CalendarGraph
import tmg.flashback.feature.season.presentation.calendar.NavigationWeekend
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsGraph
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsNavigation
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsGraph
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsNavigation
import tmg.flashback.navigation.Screen
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.presentation.settings.AllSettingsGraph
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.navigation.rememberMasterDetailPaneState

@Composable
fun AppGraph(
    appNavigationViewModel: AppNavigationViewModel,
    navController: NavHostController,
    insetPadding: PaddingValues,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    modifier: Modifier = Modifier,
) {
    val calendarNavigator = rememberMasterDetailPaneState<NavigationWeekend>()
    val driverStandingsNavigator = rememberMasterDetailPaneState<DriverStandingsNavigation>()
    val teamStandingsNavigator = rememberMasterDetailPaneState<TeamStandingsNavigation>()
    LaunchedEffect(
        calendarNavigator.destination,
        driverStandingsNavigator.destination,
        teamStandingsNavigator.destination
    ) {
        val forceHide = calendarNavigator.destination != null ||
                driverStandingsNavigator.destination != null ||
                teamStandingsNavigator.destination != null
        appNavigationViewModel.hideBar(forceHide)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Calendar,
        modifier = modifier
    ) {
        composable<Screen.Calendar> {
            CalendarGraph(
                navigator = calendarNavigator,
                paddingValues = insetPadding,
                actionUpClicked = { },
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
        composable<Screen.DriverStandings> {
            DriverStandingsGraph(
                navigator = driverStandingsNavigator,
                paddingValues = insetPadding,
                actionUpClicked = { },
                windowSizeClass = windowAdaptiveInfo.windowSizeClass,
            )
        }
        composable<Screen.TeamStandings> {
            TeamStandingsGraph(
                navigator = teamStandingsNavigator,
                paddingValues = insetPadding,
                actionUpClicked = { },
                windowSizeClass = windowAdaptiveInfo.windowSizeClass,
            )
        }
        composable<Screen.Search> {
            Box(Modifier.fillMaxSize()) {
                TextTitle("Search", Modifier.align(Alignment.Center))
            }
        }
        composable<Screen.Rss> {
            RssFeedGraph(
                paddingValues = insetPadding,
                actionUpClicked = { },
                windowSizeClass = windowAdaptiveInfo.windowSizeClass
            )
        }
        composable<Screen.Settings> {
            AllSettingsGraph(
                windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                actionUpClicked = { },
                insetPadding = insetPadding
            )
        }
        composable<Screen.ReactionGame> {
            Box(Modifier.fillMaxSize()) {
                TextTitle("Reaction Game", Modifier.align(Alignment.Center))
            }
        }
        composable<Screen.About> {
            Box(Modifier.fillMaxSize()) {
                TextTitle("About", Modifier.align(Alignment.Center))
            }
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