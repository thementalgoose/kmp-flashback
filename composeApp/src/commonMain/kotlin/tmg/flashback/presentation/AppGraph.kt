package tmg.flashback.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tmg.flashback.navigation.Screen
import tmg.flashback.presentation.navigation.AppNavigationUIState
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.presentation.settings.SettingsScreen

@Composable
fun AppGraph(
    appNavigationViewModel: AppNavigationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Settings,
        modifier = modifier
    ) {
        composable<Screen.Results> {

        }
        composable<Screen.Search> {

        }
        composable<Screen.Rss> {

        }
        composable<Screen.Settings> {
            SettingsScreen()
        }
        composable<Screen.Driver> {

        }
        composable<Screen.Constructor> {

        }
        composable<Screen.Circuit> {

        }
        composable<Screen.About> {

        }
    }
}