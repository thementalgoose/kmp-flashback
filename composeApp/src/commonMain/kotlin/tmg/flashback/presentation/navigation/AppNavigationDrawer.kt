package tmg.flashback.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.app_version_placeholder
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.navigation.Screen
import tmg.flashback.presentation.MenuItem
import tmg.flashback.presentation.icon
import tmg.flashback.presentation.label
import tmg.flashback.presentation.navigation.hero.DashboardHero
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2

@Composable
internal fun AppNavigationDrawer(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (Screen) -> Unit,
    closeMenu: () -> Unit,
    modifier: Modifier = Modifier,
    insetPadding: PaddingValues = WindowInsets.safeContent.asPaddingValues()
) {
    Box(modifier) {
        LazyColumn(
            contentPadding = insetPadding,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall),
            modifier = Modifier.fillMaxHeight(),
            content = {
                item("header") {
                    DashboardHero(
                        modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                        menuIcons = appNavigationUiState.easterEggs.menuIcon,
                        showUkraine = appNavigationUiState.easterEggs.ukraine
                    )
                }
                item("header_div") {
                    MenuDivider()
                }
                item("nav_results") {
                    NavigationItem(
                        menuItem = MenuItem.Results,
                        isSelected = appNavigationUiState.screen == Screen.Calendar ||
                                appNavigationUiState.screen == Screen.DriverStandings ||
                                appNavigationUiState.screen == Screen.TeamStandings,
                        onClick = {
                            navigationItemClicked(Screen.Calendar)
                            closeMenu()
                        }
                    )
                }
                item("nav_circuits") {
                    NavigationItem(
                        menuItem = MenuItem.Circuits,
                        isSelected = appNavigationUiState.screen == Screen.Circuits,
                        onClick = {
                            navigationItemClicked(Screen.Circuits)
                            closeMenu()
                        }
                    )
                }
                if (appNavigationUiState.showRss) {
                    item("nav_rss") {
                        NavigationItem(
                            menuItem = MenuItem.Rss,
                            isSelected = appNavigationUiState.screen == Screen.Rss,
                            onClick = {
                                navigationItemClicked(Screen.Rss)
                                closeMenu()
                            }
                        )
                    }
                }
                item("nav_reaction_game") {
                    NavigationItem(
                        menuItem = MenuItem.ReactionGame,
                        isSelected = appNavigationUiState.screen == Screen.ReactionGame,
                        onClick = {
                            navigationItemClicked(Screen.ReactionGame)
                            closeMenu()
                        }
                    )
                }
                item("nav_settings") {
                    NavigationItem(
                        menuItem = MenuItem.Settings,
                        isSelected = appNavigationUiState.screen == Screen.Settings,
                        onClick = {
                            navigationItemClicked(Screen.Settings)
                            closeMenu()
                        }
                    )
                }
                item("nav_contact") {
                    NavigationItem(
                        menuItem = MenuItem.Contact,
                        isSelected = appNavigationUiState.screen == Screen.About,
                        onClick = {
                            navigationItemClicked(Screen.About)
                            closeMenu()
                        }
                    )
                }
                item("footer_div") {
                    MenuDivider()
                }
                item("app_version") {
                    TextBody2(
                        modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                        text = stringResource(Res.string.app_version_placeholder, Device.versionName)
                    )
                }
            }
        )
    }
}

@Composable
private fun NavigationItem(
    menuItem: MenuItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(
            end = AppTheme.dimens.medium / 2
        )
        .clip(
            RoundedCornerShape(
                topEnd = AppTheme.dimens.radiusLarge,
                bottomEnd = AppTheme.dimens.radiusLarge
            )
        )
        .background(
            if (isSelected) AppTheme.colors.primaryContainer else Color.Transparent
        )
        .clickable(onClick = onClick)
        .padding(
            top = AppTheme.dimens.nsmall,
            bottom = AppTheme.dimens.nsmall,
            start = AppTheme.dimens.medium,
            end = AppTheme.dimens.medium / 2
        )
    ) {
        Icon(
            painter = painterResource(resource = menuItem.icon),
            modifier = Modifier.size(20.dp),
            tint = AppTheme.colors.onSurface,
            contentDescription = null
        )
        Spacer(Modifier.width(AppTheme.dimens.medium))
        TextBody1(
            text = stringResource(resource = menuItem.label),
            bold = true,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}