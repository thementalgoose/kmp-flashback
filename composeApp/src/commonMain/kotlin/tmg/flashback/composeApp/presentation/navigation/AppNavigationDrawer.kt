package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import flashback.composeapp.generated.resources.ic_settings_web
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.app_version_placeholder
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.icon
import tmg.flashback.composeApp.presentation.label
import tmg.flashback.composeApp.presentation.navigation.hero.DashboardHero
import tmg.flashback.composeApp.presentation.selectedIcon
import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavLineup
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.xr.LocalXR
import tmg.flashback.xr.XR

@Composable
internal fun AppNavigationDrawer(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (NavKey) -> Unit,
    closeMenu: () -> Unit,
    openUrl: (String) -> Unit,
    modifier: Modifier = Modifier,
    insetPadding: PaddingValues = WindowInsets.safeContent.asPaddingValues(),
    showXr: Boolean = false,
    xr: XR? = LocalXR.current,
) {
    Box(modifier) {
        LazyColumn(
            contentPadding = insetPadding,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall),
            modifier = Modifier.fillMaxHeight(),
            content = {
                item("header") {
                    DashboardHero(
                        modifier = Modifier.padding(
                            start = AppTheme.dimens.medium,
                            end = AppTheme.dimens.medium,
                            top = AppTheme.dimens.nsmall
                        ),
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
                        isSelected = appNavigationUiState.screen == NavCalendar ||
                                appNavigationUiState.screen == NavDriverStandings ||
                                appNavigationUiState.screen == NavTeamStandings,
                        onClick = {
                            navigationItemClicked(NavCalendar)
                            closeMenu()
                        }
                    )
                }
                item("nav_circuits") {
                    NavigationItem(
                        menuItem = MenuItem.Circuits,
                        isSelected = appNavigationUiState.screen == NavCircuits,
                        onClick = {
                            navigationItemClicked(NavCircuits)
                            closeMenu()
                        }
                    )
                }
                item("mid_div") {
                    MenuDivider()
                }
                if (appNavigationUiState.showRss) {
                    item("nav_rss") {
                        NavigationItem(
                            menuItem = MenuItem.Rss,
                            isSelected = appNavigationUiState.screen == NavRss,
                            onClick = {
                                navigationItemClicked(NavRss)
                                closeMenu()
                            }
                        )
                    }
                }
                item("nav_lineup") {
                    NavigationItem(
                        menuItem = MenuItem.Lineup,
                        isSelected = appNavigationUiState.screen == NavLineup,
                        onClick = {
                            navigationItemClicked(NavLineup)
                            closeMenu()
                        }
                    )
                }
                item("nav_reaction_game") {
                    NavigationItem(
                        menuItem = MenuItem.ReactionGame,
                        isSelected = appNavigationUiState.screen == NavReactionGame,
                        onClick = {
                            navigationItemClicked(NavReactionGame)
                            closeMenu()
                        }
                    )
                }
                item("nav_settings") {
                    NavigationItem(
                        menuItem = MenuItem.Settings,
                        isSelected = appNavigationUiState.screen == NavSettings,
                        onClick = {
                            navigationItemClicked(NavSettings)
                            closeMenu()
                        }
                    )
                }
                item("nav_contact") {
                    NavigationItem(
                        menuItem = MenuItem.Contact,
                        isSelected = appNavigationUiState.screen == NavAbout,
                        onClick = {
                            navigationItemClicked(NavAbout)
                            closeMenu()
                        }
                    )
                }
                if (showXr) {
                    item("xr_div") {
                        MenuDivider()
                    }
                    item("nav_xr") {
                        NavigationItem(
                            menuItem = MenuItem.XR_Spacial,
                            isSelected = false,
                            onClick = {
                                xr?.requestImmersiveMode()
                            }
                        )
                    }
                }
                if (appNavigationUiState.extraLinks.isNotEmpty()) {
                    item("extra_div") {
                        MenuDivider()
                    }
                }
                items(appNavigationUiState.extraLinks, key = { it.id }) {
                    when (it) {
                        is NavLink.Url -> {
                            NavigationItem(
                                label = it.name,
                                icon = flashback.composeapp.generated.resources.Res.drawable.ic_settings_web,
                                isSelected = false,
                                onClick = {
                                    openUrl(it.url)
                                }
                            )
                        }
                    }
                }
                item("footer_div") {
                    MenuDivider()
                }
                item("app_version") {
                    if (!LocalInspectionMode.current) {
                        Footer(
                            modifier = Modifier.padding(
                                vertical = AppTheme.dimens.small,
                                horizontal = AppTheme.dimens.medium
                            )
                        )
                    }
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
    NavigationItem(
        icon = menuItem.icon,
        selectedIcon = menuItem.selectedIcon,
        label = stringResource(menuItem.label),
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun NavigationItem(
    icon: DrawableResource,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: DrawableResource = icon,
) {
    val backgroundColor = animateColorAsState(targetValue = when (isSelected) {
        true -> AppTheme.colors.primary.copy(alpha = 0.3f)
        else -> Color.Transparent
    }, label = "backgroundColor")
    val contentColor = animateColorAsState(targetValue = when (isSelected) {
        true -> AppTheme.colors.onPrimaryContainer
        else -> AppTheme.colors.onSurface
    })
    val iconTransition = animateFloatAsState(targetValue = when (isSelected) {
        true -> 1f
        false -> 0f
    }, label = "iconTransition")
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
        .background(backgroundColor.value)
        .clickable(onClick = onClick)
        .padding(
            top = AppTheme.dimens.nsmall,
            bottom = AppTheme.dimens.nsmall,
            start = AppTheme.dimens.medium,
            end = AppTheme.dimens.medium / 2
        )
    ) {
        Box(modifier = Modifier
            .clearAndSetSemantics {
                this.contentDescription = label
            }
            .size(24.dp)
        ) {
            Icon(
                modifier = Modifier
                    .alpha(1f - iconTransition.value)
                    .size(24.dp),
                painter = painterResource(resource = icon),
                tint = AppTheme.colors.onSurface,
                contentDescription = null
            )
            Icon(
                modifier = Modifier
                    .alpha(iconTransition.value)
                    .size(24.dp),
                painter = painterResource(resource = selectedIcon),
                tint = AppTheme.colors.onPrimaryContainer,
                contentDescription = null
            )
        }
        Spacer(Modifier.width(AppTheme.dimens.medium))
        TextBody1(
            text = label,
            textColor = contentColor.value,
            bold = isSelected,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    version: String = "${Device.versionName}.${Device.versionCode}",
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        TextBody1(
            text = stringResource(Res.string.app_version_placeholder, "")
        )
        TextBody1(
            text = version,
            bold = true
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        AppNavigationDrawer(
            appNavigationUiState = AppNavigationUIState(
                showRss = true,
                easterEggs = AppNavigationEasterEggs(MenuIcons.HALLOWEEN, false, false, false),
                screen = NavCalendar,
                intoSubNavigation = false,
                promptContentSync = false,
                promptSoftUpgrade = false,
                extraLinks = emptyList()
            ),
            navigationItemClicked = { },
            closeMenu = { },
            openUrl = { },
            xr = null
        )
    }
}