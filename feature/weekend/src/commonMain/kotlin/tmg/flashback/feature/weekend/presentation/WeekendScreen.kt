package tmg.flashback.feature.weekend.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WeekendScreen(
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: WeekendViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    WeekendScreen(
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
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: WeekendUiState,
    refresh: () -> Unit,
) {

}