package tmg.flashback.feature.season.presentation.calendar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass.Companion.COMPACT
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.weekend.presentation.WeekendScreen
import tmg.flashback.feature.weekend.presentation.WeekendScreenData
import tmg.flashback.ui.navigation.MasterDetailPaneState
import tmg.flashback.ui.navigation.MasterDetailsPane
import tmg.flashback.ui.navigation.appBarMaximumHeight

data class WeekendNavigation(
    val season: Int,
    val round: Int,
    val raceName: String,
)

@Composable
fun CalendarGraph(
    paddingValues: PaddingValues,
    navigator: MasterDetailPaneState<WeekendNavigation>,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: CalendarScreenViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

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
                goToWeekend = {
                    navigator.navigateTo(WeekendNavigation(
                        season = it.model.season,
                        round = it.model.round,
                        raceName = it.model.raceName
                    ))
                },
                expandGroupedRaces = viewModel::clickGroupedRaces
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            val screenData = remember(model) {
                WeekendScreenData(model.season, model.round, model.raceName)
            }
            WeekendScreen(
                screenData = screenData,
                paddingValues = paddingValues,
                showBack = windowSizeClass.windowWidthSizeClass == COMPACT,
                actionUpClicked = actionUpClicked,
                windowSizeClass = windowSizeClass
            )
        }
    )
}