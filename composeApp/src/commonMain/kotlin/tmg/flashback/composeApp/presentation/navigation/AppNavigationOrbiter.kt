package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import tmg.flashback.infrastructure.extensions.toEnum
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
import tmg.flashback.composeApp.presentation.toScreen
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.navigation.NavigationOrbiter
import tmg.flashback.ui.navigation.navigationOrbiterColumnWidth
import tmg.flashback.xr.LocalXR
import tmg.flashback.xr.components.XROrbiter

@Composable
fun AppNavigationOrbiter(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (NavKey) -> Unit,
) {
    XROrbiter(
        offset = navigationOrbiterColumnWidth + AppTheme.dimens.small
    ) {
        val primaryItems = listOfNotNull(
            Calendar.toNavigationItem(appNavigationUiState.screen == NavCalendar),
            DriversStandings.toNavigationItem(appNavigationUiState.screen == NavDriverStandings),
            TeamsStandings.toNavigationItem(appNavigationUiState.screen == NavTeamStandings),
            Circuits.toNavigationItem(appNavigationUiState.screen == NavCircuits),
        )
        val secondaryItems = listOfNotNull(
            Rss.toNavigationItem(appNavigationUiState.screen == NavRss).takeIf { appNavigationUiState.showRss },
            ReactionGame.toNavigationItem(appNavigationUiState.screen == NavReactionGame),
            Settings.toNavigationItem(appNavigationUiState.screen == NavSettings),
            Contact.toNavigationItem(appNavigationUiState.screen == NavAbout)
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