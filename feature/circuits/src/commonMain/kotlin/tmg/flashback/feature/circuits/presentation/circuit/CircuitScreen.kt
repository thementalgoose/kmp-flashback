package tmg.flashback.feature.circuits.presentation.circuit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.circuits.presentation.all.CircuitNavigation
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun CircuitScreen(
    circuitNavigation: CircuitNavigation,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: CircuitViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    CircuitScreen(
        circuitNavigation = circuitNavigation,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        showBack = showBack,
        refresh = viewModel::refresh,
        uiState = uiState.value
    )
}

@Composable
private fun CircuitScreen(
    circuitNavigation: CircuitNavigation,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    showBack: Boolean,
    uiState: CircuitUiState,
    refresh: () -> Unit,
) {
    SwipeRefresh(
        isLoading = uiState.isLoading,
        onRefresh = refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            item("header") {
                Header(
                    actionUpClicked = actionUpClicked,
                    action = HeaderAction.BACK.takeIf { showBack },
                    text = uiState.circuit?.name ?: circuitNavigation.circuitName
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        CircuitScreen(
            circuitNavigation = CircuitNavigation("id", "Silverstone"),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            uiState = CircuitUiState(
                isLoading = false,
                circuit = Circuit.preview()
            ),
            refresh = { },
        )
    }
}