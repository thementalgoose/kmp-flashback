package tmg.flashback.composeApp.presentation.paddock

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.about_this_app_dependency_rss
import flashback.presentation.localisation.generated.resources.nav_paddock
import flashback.presentation.localisation.generated.resources.nav_rss
import flashback.presentation.localisation.generated.resources.nav_search
import flashback.presentation.localisation.generated.resources.reaction_screen_subtitle
import flashback.presentation.localisation.generated.resources.reaction_screen_title
import flashback.presentation.localisation.generated.resources.title_rss
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.arrow_more
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSearch
import tmg.flashback.navigation.Screen
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.buttons.ButtonTertiary
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextTitle
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
        uiState = uiState.value
    )
}

@Composable
fun PaddockScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    navigateTo: (Screen) -> Unit,
    uiState: PaddockUiState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues
    ) {
        item(key = "header") {
            Header(
                text = stringResource(string.nav_paddock),
                action = null
            )
        }
        if (uiState.isRssEnabled) {
            item(key = "rss") {
                RSS()
            }
        }
        item(key = "reaction_game") {
            ReactionGame()
        }
        item(key = "search") {
            Search()
        }
    }
}

@Composable
private fun RSS(
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = stringResource(string.title_rss),
        subtitle = null
    )
}

@Composable
private fun ReactionGame(
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = stringResource(string.reaction_screen_title),
        subtitle = null
    )
}

@Composable
private fun Search(
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier,
        title = "Search",
        subtitle = "Info",
        content = {
            FlowRow {
                BadgeView("Drivers")
                BadgeView("Teams")
                BadgeView("Races")
                BadgeView("Circuits")
            }
        }
    )
}

@Composable
private fun Container(
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
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
                end = AppTheme.dimens.small,
                top = AppTheme.dimens.small,
                bottom = AppTheme.dimens.small
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
            ) {
                TextTitle(title)
                if (subtitle != null) {
                    TextBody1(subtitle)
                }
            }
            Icon(
                painter = painterResource(Res.drawable.arrow_more),
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
            uiState = PaddockUiState(
                isRssEnabled = true
            )
        )
    }
}