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
import androidx.compose.material3.NavigationItemIconPosition
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
import tmg.flashback.navigation.Screen
import flashback.composeapp.generated.resources.ic_settings_web
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.app_version_placeholder
import flashback.presentation.localisation.generated.resources.nav_style_guide
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.generateNavPrimaryItems
import tmg.flashback.composeApp.presentation.generateNavSecondaryItems
import tmg.flashback.composeApp.presentation.generateNavTertiaryItems
import tmg.flashback.composeApp.presentation.icon
import tmg.flashback.composeApp.presentation.label
import tmg.flashback.composeApp.presentation.navigation.hero.DashboardHero
import tmg.flashback.composeApp.presentation.selectedIcon
import tmg.flashback.composeApp.presentation.toNavigationItem
import tmg.flashback.composeApp.presentation.toScreen
import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavGlossary
import tmg.flashback.navigation.NavLineup
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavStyleGuide
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.navigation.NavigationItem
import tmg.flashback.xr.LocalXR
import tmg.flashback.xr.XR

@Composable
internal fun AppNavigationDrawer(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (Screen) -> Unit,
    closeMenu: () -> Unit,
    openUrl: (String) -> Unit,
    modifier: Modifier = Modifier,
    insetPadding: PaddingValues = WindowInsets.safeContent.asPaddingValues(),
    xr: XR? = LocalXR.current,
) {
    val primaryItems = generateNavPrimaryItems(
        currentlySelectedScreen = appNavigationUiState.screen,
    )
    val secondaryItems = generateNavSecondaryItems(
        currentlySelectedScreen = appNavigationUiState.screen,
        showRss = appNavigationUiState.showRss
    )
    val tertiaryItems = generateNavTertiaryItems(
        currentlySelectedScreen = appNavigationUiState.screen,
        showXr = xr?.isXrDevice == true,
        isXrSelected = false
    )
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
                    )
                }
                item("header_div") {
                    MenuDivider()
                }
                items(primaryItems, { it.id }) {
                    NavItem(
                        menuItem = it,
                        isSelected = it.isSelected == true,
                        onClick = {
                            val item = it.id.toEnum<MenuItem> { it.key }
                            val screen = item?.toScreen() ?: return@NavItem
                            navigationItemClicked(screen)
                            closeMenu()
                        }
                    )
                }
                item("mid_div") {
                    MenuDivider()
                }
                items(secondaryItems, { it.id }) {
                    NavItem(
                        menuItem = it,
                        isSelected = it.isSelected == true,
                        onClick = {
                            val item = it.id.toEnum<MenuItem> { it.key }
                            val screen = item?.toScreen() ?: return@NavItem
                            navigationItemClicked(screen)
                            closeMenu()
                        }
                    )
                }
                if (tertiaryItems.isNotEmpty()) {
                    item("xr_div") {
                        MenuDivider()
                    }
                    items(tertiaryItems, { it.id }) {
                        NavItem(
                            menuItem = it,
                            isSelected = it.isSelected == true,
                            onClick = {
                                val item = it.id.toEnum<MenuItem> { it.key }
                                when (item) {
                                    MenuItem.XR_Spacial -> xr?.requestImmersiveMode()
                                    else -> {
                                        val screen = item?.toScreen() ?: return@NavItem
                                        navigationItemClicked(screen)
                                        closeMenu()
                                    }
                                }
                            }
                        )
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
private fun NavItem(
    menuItem: NavigationItem,
    isSelected: Boolean,
    onClick: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavItem(
        icon = menuItem.icon,
        selectedIcon = menuItem.selectedIcon,
        label = stringResource(menuItem.label),
        isSelected = isSelected,
        onClick = {
            onClick(menuItem)
        },
        modifier = modifier,
    )
}

@Composable
private fun NavItem(
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
    version: String = "${Device.versionName} (${Device.versionCode})",
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
                easterEggs = AppNavigationEasterEggs(MenuIcons.HALLOWEEN, false, false),
                screen = NavCalendar,
                intoSubNavigation = false,
                promptContentSync = false,
                promptSoftUpgrade = false,
            ),
            navigationItemClicked = { },
            closeMenu = { },
            openUrl = { },
            xr = null
        )
    }
}