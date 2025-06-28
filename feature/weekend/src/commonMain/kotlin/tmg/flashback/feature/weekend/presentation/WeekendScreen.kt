package tmg.flashback.feature.weekend.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_qualifying
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.weekend.presentation.WeekendUiState.Data
import tmg.flashback.feature.weekend.presentation.data.info.RaceDetails
import tmg.flashback.feature.weekend.presentation.data.info.Schedule
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingHeader
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingModel
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingResult
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun WeekendScreen(
    screenData: WeekendScreenData,
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: WeekendViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    LaunchedEffect(screenData) {
        viewModel.load(
            season = screenData.season,
            round = screenData.round
        )
    }
    WeekendScreen(
        isLoading = isLoading.value,
        screenData = screenData,
        paddingValues = paddingValues,
        showBack = showBack,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        refresh = viewModel::refresh
    )
}


@Composable
fun WeekendScreen(
    screenData: WeekendScreenData,
    isLoading: Boolean,
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: WeekendUiState,
    refresh: () -> Unit,
) {
    SwipeRefresh(
        isLoading = isLoading,
        onRefresh = refresh,
        content = {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                item("header") {
                    Header(
                        actionUpClicked = actionUpClicked,
                        action = HeaderAction.BACK.takeIf { showBack },
                        text = (uiState as? Data)?.info?.raceName ?: screenData.raceName
                    )
                }
                if (uiState is Data) {
                    item("schedule") {
                        Column {
                            RaceDetails(
                                model = uiState.info,
                                modifier = Modifier.padding(
                                    horizontal = AppTheme.dimens.medium,
                                    vertical = AppTheme.dimens.xsmall
                                )
                            )
                            Schedule(
                                model = uiState.info
                            )
                        }
                    }
                    item("qualifying_header") {
                        TypeHeader(string.nav_qualifying)
                    }
                    items(uiState.qualifyingResults, key = { it.id }) {
                        when (it) {
                            is QualifyingModel.Q1 -> QualifyingResult(
                                model = it,
                                driverClicked = { }
                            )
                            is QualifyingModel.Q1Q2 -> QualifyingResult(
                                model = it,
                                driverClicked = { }
                            )
                            is QualifyingModel.Q1Q2Q3 -> QualifyingResult(
                                model = it,
                                driverClicked = { }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun TypeHeader(
    resource: StringResource,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.small
        )
) {
    TextHeadline2(
        text = stringResource(resource),
        modifier = modifier
    )
}