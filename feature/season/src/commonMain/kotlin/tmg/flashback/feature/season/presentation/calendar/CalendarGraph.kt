package tmg.flashback.feature.season.presentation.calendar

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

data class NavigationWeekend(
    val season: Int,
    val round: Int
)

@Composable
fun CalendarGraph(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: CalendarScreenViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val navigator = rememberMasterDetailPaneState<NavigationWeekend>()


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
            CalendarScreen(
                paddingValues = masterPadding,
                actionUpClicked = actionUpClicked,
                windowSizeClass = windowSizeClass,
                uiState = uiState.value,
                refresh = viewModel::refresh,
                itemClicked = {

                },
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