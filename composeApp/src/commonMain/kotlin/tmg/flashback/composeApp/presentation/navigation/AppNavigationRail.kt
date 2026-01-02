package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import kotlinx.coroutines.coroutineScope
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
import tmg.flashback.composeApp.presentation.toScreen
import tmg.flashback.ui.navigation.NavigationColumn
import tmg.flashback.xr.LocalXR

@Composable
internal fun AppNavigationRail(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (Screen) -> Unit,
    insetPadding: PaddingValues,
    showXr: Boolean = false,
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
    val tertiaryItems = listOfNotNull(
        XR_Spacial.toNavigationItem(false).takeIf { showXr }
    )

    val xr = LocalXR.current
    NavigationColumn(
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