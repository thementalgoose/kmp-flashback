package tmg.flashback.feature.season.presentation.driver_standings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.navigation.MasterDetailsPane
import tmg.flashback.ui.navigation.rememberMasterDetailPaneState

private interface Navigation {
    data class Driver(
        val id: String,
        val name: String
    ): Navigation
}

@Composable
fun DriverStandingsGraph(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: DriverStandingsViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    val navigator = rememberMasterDetailPaneState<Navigation>()

    MasterDetailsPane(
        navigator = navigator,
        windowSizeClass = windowSizeClass,
        master = {
            DriverStandingsScreen(
                paddingValues = paddingValues,
                actionUpClicked = actionUpClicked,
                uiState = state.value,
                windowSizeClass = windowSizeClass,
                driverClicked = {
                    navigator.navigateTo(Navigation.Driver(id = it.driver.id, name = it.driver.name))
                },
                refresh = viewModel::refresh,
                comparisonClicked = {
                    // TODO
                }
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            TextTitle("Model $model")
        }
    )
}