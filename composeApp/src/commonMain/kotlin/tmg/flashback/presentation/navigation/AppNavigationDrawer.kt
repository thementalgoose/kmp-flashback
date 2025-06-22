package tmg.flashback.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.presentation.MenuItem
import tmg.flashback.presentation.icon
import tmg.flashback.presentation.label
import tmg.flashback.presentation.navigation.hero.DashboardHero
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1

@Composable
internal fun AppNavigationDrawer(
    appNavigationUiState: AppNavigationUIState,
    navigationItemClicked: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        LazyColumn(
            contentPadding = WindowInsets.safeContent.asPaddingValues(),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall),
            modifier = Modifier.fillMaxHeight(),
            content = {
                item("header") {
                    DashboardHero(
                        modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                        menuIcons = appNavigationUiState.menuIcon,
                        showUkraine = appNavigationUiState.ukraine
                    )
                }
                item("header_div") {
                    MenuDivider()
                }
                item("nav_results") {
                    NavigationItem(
                        menuItem = MenuItem.Calendar,
                        isSelected = true,
                        onClick = { navigationItemClicked(MenuItem.Calendar) }
                    )
                }
                item("nav_search") {
                    NavigationItem(
                        menuItem = MenuItem.Search,
                        isSelected = false,
                        onClick = { navigationItemClicked(MenuItem.Search) }
                    )
                }
                if (appNavigationUiState.showRss) {
                    item("nav_rss") {
                        NavigationItem(
                            menuItem = MenuItem.Rss,
                            isSelected = false,
                            onClick = { navigationItemClicked(MenuItem.Rss) }
                        )
                    }
                }
                item("nav_settings") {
                    NavigationItem(
                        menuItem = MenuItem.Settings,
                        isSelected = false,
                        onClick = { navigationItemClicked(MenuItem.Settings) }
                    )
                }
                item("nav_contact") {
                    NavigationItem(
                        menuItem = MenuItem.Contact,
                        isSelected = false,
                        onClick = { navigationItemClicked(MenuItem.Contact) }
                    )
                }
                item("footer_div") {
                    MenuDivider()
                }
            }
        )
    }
}

@Composable
private fun MenuDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.xsmall
        ),
        color = AppTheme.colors.surfaceContainer5
    )
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