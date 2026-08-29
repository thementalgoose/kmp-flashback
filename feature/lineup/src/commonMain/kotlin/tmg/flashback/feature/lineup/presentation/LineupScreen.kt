package tmg.flashback.feature.lineup.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

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
        uiState = uiState.value
    )
}

@Composable
private fun LineupScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: LineupUiState
) {

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
        )
    }
}