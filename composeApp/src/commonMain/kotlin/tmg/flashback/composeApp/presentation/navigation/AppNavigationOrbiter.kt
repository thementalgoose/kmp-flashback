package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.runtime.Composable
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.MenuItem.XR_Spacial
import tmg.flashback.composeApp.presentation.generateNavPrimaryItems
import tmg.flashback.composeApp.presentation.generateNavSecondaryItems
import tmg.flashback.composeApp.presentation.generateNavTertiaryItems
import tmg.flashback.composeApp.presentation.toScreen
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.navigation.Screen
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
        val primaryItems = generateNavPrimaryItems(
            currentlySelectedScreen = appNavigationUiState.screen
        )
        val secondaryItems = generateNavSecondaryItems(
            currentlySelectedScreen = appNavigationUiState.screen,
            showRss = appNavigationUiState.showRss
        )
        val tertiaryItems = generateNavTertiaryItems(
            currentlySelectedScreen = appNavigationUiState.screen,
            showXr = true,
            isXrSelected = true
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
                    null -> { /* Do nothing */ }
                    else -> {
                        val result = item.toScreen() ?: return@NavigationOrbiter
                        navigationItemClicked(result)
                    }
                }
            }
        )
    }
}