package tmg.flashback.feature.season.presentation.driver_standings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.drivers.presentation.comparison.DriverComparisonScreen
import tmg.flashback.feature.drivers.presentation.season.DriverSeasonInfo
import tmg.flashback.feature.drivers.presentation.season.DriverSeasonScreen
import tmg.flashback.ui.navigation.MasterDetailPaneState
import tmg.flashback.ui.navigation.MasterDetailsPane
import tmg.flashback.ui.navigation.appBarMaximumHeight

sealed interface DriverStandingsNavigation {
    data class Driver(
        val season: Int,
        val id: String,
        val name: String
    ): DriverStandingsNavigation
    data class Comparison(
        val season: Int
    ): DriverStandingsNavigation
}

@Composable
fun DriverStandingsGraph(
    navigator: MasterDetailPaneState<DriverStandingsNavigation>,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: DriverStandingsViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    // Add custom padding for nav bar
    val direction = LocalLayoutDirection.current
    val masterPadding = PaddingValues(
        top = paddingValues.calculateTopPadding(),
        bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
        start = paddingValues.calculateStartPadding(direction),
        end = paddingValues.calculateEndPadding(direction)
    )

    MasterDetailsPane(
        navigator = navigator,
        windowSizeClass = windowSizeClass,
        master = {
            DriverStandingsScreen(
                paddingValues = masterPadding,
                actionUpClicked = actionUpClicked,
                uiState = state.value,
                windowSizeClass = windowSizeClass,
                driverClicked = {
                    navigator.navigateTo(DriverStandingsNavigation.Driver(
                        season = it.season,
                        id = it.driver.id,
                        name = it.driver.name
                    ))
                },
                refresh = viewModel::refresh,
                comparisonClicked = {
                    navigator.navigateTo(DriverStandingsNavigation.Comparison(
                        season = state.value.season
                    ))
                }
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            when (model) {
                is DriverStandingsNavigation.Driver -> DriverSeasonScreen(
                    driverSeasonInfo = DriverSeasonInfo(
                        season = model.season,
                        id = model.id,
                        name = model.name,
                    ),
                    paddingValues = paddingValues,
                    actionUpClicked = actionUpClicked,
                    showBack = true,
                    windowSizeClass = windowSizeClass
                )
                is DriverStandingsNavigation.Comparison -> DriverComparisonScreen(
                    paddingValues = paddingValues,
                    actionUpClicked = actionUpClicked,
                    windowSizeClass = windowSizeClass,
                    season = model.season,
                )
            }
        }
    )
}