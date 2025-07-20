package tmg.flashback.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.navigation.Screen
import tmg.flashback.presentation.MenuItem
import tmg.flashback.presentation.MenuItem.Calendar
import tmg.flashback.presentation.MenuItem.Circuits
import tmg.flashback.presentation.MenuItem.Contact
import tmg.flashback.presentation.MenuItem.DriversStandings
import tmg.flashback.presentation.MenuItem.ReactionGame
import tmg.flashback.presentation.MenuItem.Rss
import tmg.flashback.presentation.MenuItem.Settings
import tmg.flashback.presentation.MenuItem.TeamsStandings
import tmg.flashback.presentation.toNavigationItem
import tmg.flashback.presentation.toScreen
import tmg.flashback.ui.navigation.NavigationColumn

@Composable
internal fun AppNavigationRail(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (Screen) -> Unit,
    insetPadding: PaddingValues,

) {
    val primaryItems = listOfNotNull(
        Calendar.toNavigationItem(appNavigationUiState.screen == Screen.Calendar),
        DriversStandings.toNavigationItem(appNavigationUiState.screen == Screen.DriverStandings),
        TeamsStandings.toNavigationItem(appNavigationUiState.screen == Screen.TeamStandings)
    )
    val secondaryItems = listOfNotNull(
        Circuits.toNavigationItem(appNavigationUiState.screen == Screen.Circuits),
        Rss.toNavigationItem(appNavigationUiState.screen == Screen.Rss).takeIf { appNavigationUiState.showRss },
        ReactionGame.toNavigationItem(appNavigationUiState.screen == Screen.ReactionGame),
        Settings.toNavigationItem(appNavigationUiState.screen == Screen.Settings),
        Contact.toNavigationItem(appNavigationUiState.screen == Screen.About)
    )

    NavigationColumn(
        primary = primaryItems,
        divider = { MenuDivider() },
        secondary = secondaryItems,
        itemClicked = {
            val item = it.id.toEnum<MenuItem> { it.key }
            val result = item?.toScreen()
            if (result != null) {
                navigationItemClicked(result)
            }
        },
        padding = insetPadding
    )
}