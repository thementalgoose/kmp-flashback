package tmg.flashback.feature.weekend.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.weekend.presentation.WeekendUiState.Data
import tmg.flashback.feature.weekend.presentation.data.info.Schedule
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

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
    LaunchedEffect(screenData) {
        viewModel.load(
            season = screenData.season,
            round = screenData.round
        )
    }
    WeekendScreen(
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
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: WeekendUiState,
    refresh: () -> Unit,
) {
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
                Schedule(
                    model = uiState.info
                )
            }
        }
    }
}