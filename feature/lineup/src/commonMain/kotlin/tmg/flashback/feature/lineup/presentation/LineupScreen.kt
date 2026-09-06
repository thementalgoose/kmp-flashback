@file:OptIn(ExperimentalComposeUiApi::class)

package tmg.flashback.feature.lineup.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.driver_lineup_subtitle
import flashback.presentation.localisation.generated.resources.driver_lineup_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.lineup.presentation.component.ConstructorItem
import tmg.flashback.feature.lineup.presentation.component.SeasonList
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.loading.SkeletonViewList
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun LineupScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (NavKey) -> Unit,
    viewModel: LineupViewModel = koinViewModel()
) {
    ScreenView(screenName = "Lineup")
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    val uiState = viewModel.uiState.collectAsState()
    LineupScreen(
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        refresh = viewModel::refresh,
    )
}

enum class HorizontalScrollDirection {
    IDLE,
    LEFT,
    RIGHT
}

@Composable
private fun LineupScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: LineupUiState,
    refresh: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val scrollState = rememberScrollState()
        val listState = rememberLazyListState()
        val isHeaderStuck by remember {
            derivedStateOf {
                val firstItem = listState.layoutInfo.visibleItemsInfo
                    .firstOrNull { it.index == 2 }
                firstItem != null && firstItem.offset <= 0
            }
        }
        val topPadding by animateDpAsState(
            targetValue = if (isHeaderStuck) paddingValues.calculateTopPadding() else 0.dp,
            label = "stickyHeaderPadding"
        )
        val scrimColor = when (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
            true -> AppTheme.colors.surfaceContainer1
            false -> AppTheme.colors.surface
        }

        SwipeRefresh(
            isLoading = uiState.isLoading,
            onRefresh = refresh,
            windowSizeClass = windowSizeClass,
            content = {
                LazyColumn(
                    state = listState,
                    contentPadding = paddingValues,
                    content = {
                        item("header") {
                            Header(
                                text = stringResource(string.driver_lineup_title),
                                action = when (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
                                    true -> null
                                    false -> HeaderAction.MENU
                                },
                                actionUpClicked = actionUpClicked,
                                overrideIcons = {
                                    Refresh(onClick = refresh)
                                }
                            )
                        }
                        item("subheader") {
                            Subheader(
                                modifier = Modifier.padding(horizontal = AppTheme.dimens.medium)
                            )
                        }
                        if (uiState.rows.isNotEmpty()) {
                            stickyHeader(key = "lineup-title") {
                                Column(
                                    modifier = Modifier
                                        .animateItem()
                                        .background(Brush.verticalGradient(
                                            listOf(scrimColor, AppTheme.colors.surface, Color.Transparent)
                                        ))
                                        .padding(
                                            top = AppTheme.dimens.medium + topPadding,
                                            start = AppTheme.dimens.medium,
                                            end = AppTheme.dimens.medium,
                                            bottom = AppTheme.dimens.medium
                                        )
                                ) {
                                    SeasonList(
                                        modifier = Modifier,
                                        seasons = uiState.seasons
                                    )
                                }
                            }
                            item("lineup") {
                                Column(
                                    modifier = Modifier
                                        .animateItem()
                                        .padding(
                                            horizontal = AppTheme.dimens.medium
                                        )
                                ) {
                                    for (lineup in uiState.rows) {
                                        ConstructorItem(
                                            seasonList = uiState.seasons,
                                            constructorLineup = lineup,
                                            modifier = Modifier.padding(bottom = AppTheme.dimens.medium),
                                        )
                                    }
                                }
                            }
                        } else {
                            item("placeholder") {
                                SkeletonViewList()
                            }
                        }
                    }
                )
            }
        )
    }
}

@Composable
private fun Subheader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextBody1(
            text = stringResource(string.driver_lineup_subtitle)
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        LineupScreen(
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            uiState = fakeUiState,
            refresh = { },
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewUnconfirmed() {
    ApplicationThemePreview {
        LineupScreen(
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            uiState = fakeUiUnconfirmedState,
            refresh = { },
        )
    }
}