package tmg.flashback.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
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
    insetPadding: PaddingValues,
) {
    val primaryItems = listOfNotNull(Calendar, DriversStandings, TeamsStandings)
        .map { it.toNavigationItem(true) }
    val secondaryItems = listOfNotNull(Search, Rss.takeIf { appNavigationUiState.showRss }, Settings, Contact)
        .map { it.toNavigationItem(false) }

    NavigationColumn(
        primary = primaryItems,
        divider = { MenuDivider() },
        secondary = secondaryItems,
        itemClicked = { },
        padding = insetPadding
    )
}