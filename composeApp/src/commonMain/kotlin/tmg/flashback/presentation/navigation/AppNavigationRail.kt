package tmg.flashback.presentation.navigation

import androidx.compose.runtime.Composable
import tmg.flashback.navigation.Screen
import tmg.flashback.presentation.MenuItem.Calendar
import tmg.flashback.presentation.MenuItem.Contact
import tmg.flashback.presentation.MenuItem.DriversStandings
import tmg.flashback.presentation.MenuItem.Rss
import tmg.flashback.presentation.MenuItem.Search
import tmg.flashback.presentation.MenuItem.Settings
import tmg.flashback.presentation.MenuItem.TeamsStandings
import tmg.flashback.presentation.toNavigationItem
import tmg.flashback.ui.navigation.NavigationColumn

@Composable
internal fun AppNavigationRail(
    appNavigationUiState: AppNavigationUIState,
) {
    val primaryItems = listOfNotNull(Calendar, DriversStandings, TeamsStandings)
        .map { it.toNavigationItem(false) }
    val secondaryItems = listOfNotNull(Search, Rss.takeIf { appNavigationUiState.showRss }, Settings, Contact)
        .map { it.toNavigationItem(false) }

    NavigationColumn(
        primary = primaryItems,
        divider = { MenuDivider() },
        secondary = secondaryItems,
        itemClicked = { },
    )
}