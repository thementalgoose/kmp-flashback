package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.coroutineScope
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.MenuItem.Calendar
import tmg.flashback.composeApp.presentation.MenuItem.Circuits
import tmg.flashback.composeApp.presentation.MenuItem.Contact
import tmg.flashback.composeApp.presentation.MenuItem.DriversStandings
import tmg.flashback.composeApp.presentation.MenuItem.Lineup
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
import tmg.flashback.navigation.NavLineup
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.ui.navigation.NavigationColumn
import tmg.flashback.xr.LocalXR

@Composable
internal fun AppNavigationRail(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (NavKey) -> Unit,
    insetPadding: PaddingValues,
    modifier: Modifier = Modifier,
    showXr: Boolean = false,
) {
    val primaryItems = listOfNotNull(
        Calendar.toNavigationItem(appNavigationUiState.screen == NavCalendar),
        DriversStandings.toNavigationItem(appNavigationUiState.screen == NavDriverStandings),
        TeamsStandings.toNavigationItem(appNavigationUiState.screen == NavTeamStandings),
        Circuits.toNavigationItem(appNavigationUiState.screen == NavCircuits),
        Lineup.toNavigationItem(appNavigationUiState.screen == NavLineup),
    )
    val secondaryItems = listOfNotNull(
        Rss.toNavigationItem(appNavigationUiState.screen == NavRss).takeIf { appNavigationUiState.showRss },
        ReactionGame.toNavigationItem(appNavigationUiState.screen == NavReactionGame),
        Settings.toNavigationItem(appNavigationUiState.screen == NavSettings),
        Contact.toNavigationItem(appNavigationUiState.screen == NavAbout)
    )
    val tertiaryItems = listOfNotNull(
        XR_Spacial.toNavigationItem(false).takeIf { showXr }
    )

    val xr = LocalXR.current
    NavigationColumn(
        modifier = modifier,
        primary = primaryItems,
        divider = { MenuDivider() },
        secondary = secondaryItems,
        tertiary = tertiaryItems,
        itemClicked = {
            val item = it.id.toEnum<MenuItem> { it.key }
            when (item) {
                XR_Spacial -> {
                    xr.requestImmersiveMode()
                }
                Results,
                Calendar,
                DriversStandings,
                TeamsStandings,
                Circuits,
                Lineup,
                Rss,
                ReactionGame,
                Settings,
                Contact -> {
                    val result = item.toScreen() ?: return@NavigationColumn
                    navigationItemClicked(result)
                }
                null -> { /* Do nothing */ }
            }
        },
        padding = insetPadding
    )
}