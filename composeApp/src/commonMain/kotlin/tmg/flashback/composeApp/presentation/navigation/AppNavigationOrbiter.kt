package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.runtime.Composable
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.navigation.Screen
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.MenuItem.Calendar
import tmg.flashback.composeApp.presentation.MenuItem.Circuits
import tmg.flashback.composeApp.presentation.MenuItem.Contact
import tmg.flashback.composeApp.presentation.MenuItem.DriversStandings
import tmg.flashback.composeApp.presentation.MenuItem.ReactionGame
import tmg.flashback.composeApp.presentation.MenuItem.Results
import tmg.flashback.composeApp.presentation.MenuItem.Rss
import tmg.flashback.composeApp.presentation.MenuItem.Settings
import tmg.flashback.composeApp.presentation.MenuItem.TeamsStandings
import tmg.flashback.composeApp.presentation.MenuItem.XR_Spacial
import tmg.flashback.composeApp.presentation.toNavigationItem
import tmg.flashback.presentation.toScreen
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.navigation.NavigationOrbiter
import tmg.flashback.ui.navigation.navigationOrbiterColumnWidth
import tmg.flashback.xr.LocalXR
import tmg.flashback.xr.components.XROrbiter

@Composable
fun AppNavigationOrbiter(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (Screen) -> Unit,
) {
    XROrbiter(
        offset = navigationOrbiterColumnWidth + AppTheme.dimens.small
    ) {
        val primaryItems = listOfNotNull(
            Calendar.toNavigationItem(appNavigationUiState.screen == Screen.Calendar),
            DriversStandings.toNavigationItem(appNavigationUiState.screen == Screen.DriverStandings),
            TeamsStandings.toNavigationItem(appNavigationUiState.screen == Screen.TeamStandings),
            Circuits.toNavigationItem(appNavigationUiState.screen == Screen.Circuits),
        )
        val secondaryItems = listOfNotNull(
            Rss.toNavigationItem(appNavigationUiState.screen == Screen.Rss).takeIf { appNavigationUiState.showRss },
            ReactionGame.toNavigationItem(appNavigationUiState.screen == Screen.ReactionGame),
            Settings.toNavigationItem(appNavigationUiState.screen == Screen.Settings),
            Contact.toNavigationItem(appNavigationUiState.screen == Screen.About)
        )
        val tertiaryItems = listOf(
            XR_Spacial.toNavigationItem(true)
        )

        val xr = LocalXR.current
        NavigationOrbiter(
            primary = primaryItems,
            secondary = secondaryItems,
            tertiary = tertiaryItems,
            divider = { MenuDivider() },
            itemClicked = {
                val item = it.id.toEnum<MenuItem> { it.key }
                when (item) {
                    XR_Spacial -> {
                        xr.requestPassthroughMode()
                    }
                    Results,
                    Calendar,
                    DriversStandings,
                    TeamsStandings,
                    Circuits,
                    Rss,
                    ReactionGame,
                    Settings,
                    Contact -> {
                        val result = item.toScreen() ?: return@NavigationOrbiter
                        navigationItemClicked(result)
                    }
                    null -> { /* Do nothing */ }
                }
            }
        )
    }
}