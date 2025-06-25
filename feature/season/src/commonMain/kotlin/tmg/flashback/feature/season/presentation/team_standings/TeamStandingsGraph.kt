package tmg.flashback.feature.season.presentation.team_standings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.navigation.MasterDetailsPane
import tmg.flashback.ui.navigation.appBarMaximumHeight
import tmg.flashback.ui.navigation.rememberMasterDetailPaneState

data class NavigationTeam(
    val id: String,
    val name: String
)

@Composable
fun TeamStandingsGraph(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: TeamStandingsViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    val navigator = rememberMasterDetailPaneState<NavigationTeam>()

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
                    navigator.navigateTo(NavigationTeam(id = it.constructor.id, it.constructor.name))
                },
                refresh = viewModel::refresh
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