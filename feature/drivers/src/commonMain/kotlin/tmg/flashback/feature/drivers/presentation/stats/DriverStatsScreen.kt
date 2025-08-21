package tmg.flashback.feature.drivers.presentation.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.formula1.model.Driver
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider

@Composable
fun DriverStatsScreen(
    driverId: String,
    driverName: String,
    viewModel: DriverStatsViewModel = koinViewModel(),
    startingSeason: Int? = null,
) {
    val uiState = viewModel.uiState.collectAsState()

    DriverStatsScreen(
        uiState = uiState.value,
        changeSelection = viewModel::changeSelection
    )
}

@Composable
private fun DriverStatsScreen(
    uiState: DriverStatsUiState,
    changeSelection: (Int?) -> Unit
) {

}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {

    }
}