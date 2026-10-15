package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tmg.flashback.navigation.Screen
import kotlinx.coroutines.coroutineScope
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.MenuItem.Calendar
import tmg.flashback.composeApp.presentation.MenuItem.Circuits
import tmg.flashback.composeApp.presentation.MenuItem.Contact
import tmg.flashback.composeApp.presentation.MenuItem.DriversStandings
import tmg.flashback.composeApp.presentation.MenuItem.Lineup
import tmg.flashback.composeApp.presentation.MenuItem.Glossary
import tmg.flashback.composeApp.presentation.MenuItem.Info
import tmg.flashback.composeApp.presentation.MenuItem.ReactionGame
import tmg.flashback.composeApp.presentation.MenuItem.Results
import tmg.flashback.composeApp.presentation.MenuItem.Rss
import tmg.flashback.composeApp.presentation.MenuItem.Settings
import tmg.flashback.composeApp.presentation.MenuItem.StyleGuide
import tmg.flashback.composeApp.presentation.MenuItem.TeamsStandings
import tmg.flashback.composeApp.presentation.MenuItem.XR_Spacial
import tmg.flashback.composeApp.presentation.generateNavPrimaryItems
import tmg.flashback.composeApp.presentation.generateNavSecondaryItems
import tmg.flashback.composeApp.presentation.generateNavTertiaryItems
import tmg.flashback.composeApp.presentation.toNavigationItem
import tmg.flashback.composeApp.presentation.toScreen
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavLineup
import tmg.flashback.navigation.NavGlossary
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavStyleGuide
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.ui.navigation.NavigationColumn
import tmg.flashback.xr.LocalXR

@Composable
internal fun AppNavigationRail(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (Screen) -> Unit,
    insetPadding: PaddingValues,
    modifier: Modifier = Modifier,
    showXr: Boolean = false,
) {
    val primaryItems = generateNavPrimaryItems(
        currentlySelectedScreen = appNavigationUiState.screen
    )
    val secondaryItems = generateNavSecondaryItems(
        currentlySelectedScreen = appNavigationUiState.screen,
        showRss = appNavigationUiState.showRss
    )
    val tertiaryItems = generateNavTertiaryItems(
        currentlySelectedScreen = appNavigationUiState.screen,
        showXr = showXr,
        isXrSelected = false
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
                null -> { /* Do nothing */ }
                else -> {
                    val result = item.toScreen() ?: return@NavigationColumn
                    navigationItemClicked(result)
                }
            }
        },
        padding = insetPadding
    )
}