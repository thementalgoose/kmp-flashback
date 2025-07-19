package tmg.flashback.feature.season.presentation.team_standings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.constructors.presentation.season.ConstructorSeasonInfo
import tmg.flashback.feature.constructors.presentation.season.ConstructorSeasonScreen
import tmg.flashback.feature.drivers.presentation.season.DriverSeasonInfo
import tmg.flashback.feature.drivers.presentation.season.DriverSeasonScreen
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsNavigation
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.navigation.MasterDetailPaneState
import tmg.flashback.ui.navigation.MasterDetailsPane
import tmg.flashback.ui.navigation.appBarMaximumHeight

data class TeamStandingsNavigation(
    val season: Int,
    val id: String,
    val name: String
)

@Composable
fun TeamStandingsGraph(
    navigator: MasterDetailPaneState<TeamStandingsNavigation>,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: TeamStandingsViewModel = koinViewModel()
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
            TeamStandingsScreen(
                paddingValues = masterPadding,
                actionUpClicked = actionUpClicked,
                windowSizeClass = windowSizeClass,
                uiState = state.value,
                constructorClicked = {
                    navigator.navigateTo(TeamStandingsNavigation(
                        id = it.constructor.id,
                        name = it.constructor.name,
                        season = it.season
                    ))
                },
                refresh = viewModel::refresh
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            ConstructorSeasonScreen(
               constructorSeasonInfo = ConstructorSeasonInfo(
                   season = model.season,
                   id = model.id,
                   name = model.name,
               ),
               paddingValues = paddingValues,
               actionUpClicked = actionUpClicked,
               showBack = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED,
               windowSizeClass = windowSizeClass
           )
        }
    )
}