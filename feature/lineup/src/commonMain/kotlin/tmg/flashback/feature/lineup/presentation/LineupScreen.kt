package tmg.flashback.feature.lineup.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
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
        SwipeRefresh(
            isLoading = uiState.isLoading,
            onRefresh = refresh,
            content = {
                LazyColumn(
                    contentPadding = paddingValues,
                    content = {
                        item("header") {
                            Header(
                                text = stringResource(string.driver_lineup_title),
                                action = null,
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
                        item("lineup") {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        vertical = AppTheme.dimens.medium,
                                        horizontal = AppTheme.dimens.medium
                                    )
                                    .horizontalScroll(rememberScrollState())
                            ) {
                                SeasonList(seasons = uiState.seasons)
                                for (lineup in uiState.rows) {
                                    ConstructorItem(
                                        constructorLineup = lineup,
                                    )
                                }
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