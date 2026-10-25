package tmg.flashback.composeApp.presentation.paddock

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_paddock
import flashback.presentation.localisation.generated.resources.paddock_directory_description
import flashback.presentation.localisation.generated.resources.paddock_directory_title
import flashback.presentation.localisation.generated.resources.paddock_lineup_description
import flashback.presentation.localisation.generated.resources.paddock_lineup_title
import flashback.presentation.localisation.generated.resources.paddock_reaction_game_description
import flashback.presentation.localisation.generated.resources.paddock_reaction_game_title
import flashback.presentation.localisation.generated.resources.paddock_rss_description
import flashback.presentation.localisation.generated.resources.paddock_rss_title
import flashback.presentation.localisation.generated.resources.search_category_circuits
import flashback.presentation.localisation.generated.resources.search_category_constructors
import flashback.presentation.localisation.generated.resources.search_category_driver
import flashback.presentation.localisation.generated.resources.search_category_races
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.navigation.NavLineup
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSearch
import tmg.flashback.navigation.Screen
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextHeadline3
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.navigation.appBarMaximumHeight

@Composable
fun PaddockScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    viewModel: PaddockViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val insets = when (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        true -> paddingValues
        false -> {
            val direction = LocalLayoutDirection.current
            PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
                start = paddingValues.calculateStartPadding(direction),
                end = paddingValues.calculateEndPadding(direction)
            )
        }
    }

    PaddockScreen(
        paddingValues = insets,
        windowSizeClass = windowSizeClass,
        navigateTo = navigateTo,
        openUrl = {
            viewModel.openUrl(it.url, it.name)
        },
        uiState = uiState.value
    )
}

@Composable
fun PaddockScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    openUrl: (NavLink.Url) -> Unit,
    uiState: PaddockUiState
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        columns = GridCells.Adaptive(250.dp),
    ) {
        item(key = "header", span = { GridItemSpan(maxLineSpan) }) {
            Header(
                text = stringResource(string.nav_paddock),
                action = null
            )
        }
        item(key = "lineup") {
            Lineup(
                modifier = Modifier.clickable(
                    onClick = { navigateTo(NavLineup) }
                )
            )
        }
        if (uiState.isRssEnabled) {
            item(key = "rss") {
                RSS(
                    modifier = Modifier.clickable(
                        onClick = { navigateTo(NavRss) }
                    )
                )
            }
        }
        item(key = "reaction_game") {
            ReactionGame(
                modifier = Modifier.clickable(
                    onClick = { navigateTo(NavReactionGame) }
                )
            )
        }
        item(key = "search") {
            Search(
                modifier = Modifier.clickable(
                    onClick = { navigateTo(NavSearch()) }
                )
            )
        }
        items(uiState.navLinks, key = { it.id }) {
            when (it) {
                is NavLink.Url -> {
                    Links(
                        navLink = it,
                        modifier = Modifier.clickable(
                            onClick = { openUrl(it) }
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun RSS(
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = stringResource(string.paddock_rss_title),
        subtitle = stringResource(string.paddock_rss_description),
        icon = {
            Icon(
                imageVector = Icons.Default.RssFeed,
                contentDescription = null,
                tint = AppTheme.colors.primary,
            )
        },
    )
}

@Composable
private fun Lineup(
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = stringResource(string.paddock_lineup_title),
        subtitle = stringResource(string.paddock_lineup_description),
        icon = {
            Icon(
                imageVector = Icons.Default.DriveFileRenameOutline,
                contentDescription = null,
                tint = AppTheme.colors.primary,
            )
        },
    )
}

@Composable
private fun ReactionGame(
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = stringResource(string.paddock_reaction_game_title),
        subtitle = stringResource(string.paddock_reaction_game_description),
        icon = {
            Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        },
    )
}

@Composable
private fun Search(
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = stringResource(string.paddock_directory_title),
        subtitle = stringResource(string.paddock_directory_description),
        icon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        }
    )
}

@Composable
private fun Links(
    navLink: NavLink.Url,
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = navLink.name,
        subtitle = navLink.url,
        icon = {
            Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        }
    )
}

@Composable
private fun Container(
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = { },
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xxsmall
            )
            .clip(RoundedCornerShape(6.dp))
            .background(AppTheme.colors.surfaceContainer3)
            .padding(
                start = AppTheme.dimens.medium,
                end = AppTheme.dimens.medium,
                top = AppTheme.dimens.medium,
                bottom = AppTheme.dimens.medium
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = AppTheme.dimens.small),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
            ) {
                icon?.invoke()
                TextHeadline3(
                    text = title.uppercase(),
                    bold = true,
                )
                if (subtitle != null) {
                    TextBody2(subtitle)
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        }
        content()
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        PaddockScreen(
            paddingValues = PaddingValues(0.dp),
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            navigateTo = { },
            openUrl = { },
            uiState = PaddockUiState(
                isRssEnabled = true,
                navLinks = listOf(
                    NavLink.Url("formula1", "https://formula1.com")
                )
            )
        )
    }
}
