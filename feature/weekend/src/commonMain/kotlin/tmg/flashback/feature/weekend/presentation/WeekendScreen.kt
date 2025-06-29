package tmg.flashback.feature.weekend.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.weekend.presentation.WeekendUiState.Data
import tmg.flashback.feature.weekend.presentation.data.info.InfoModel
import tmg.flashback.feature.weekend.presentation.data.info.RaceDetails
import tmg.flashback.feature.weekend.presentation.data.info.Schedule
import tmg.flashback.feature.weekend.presentation.data.qualifying.addQualifyingData
import tmg.flashback.feature.weekend.presentation.data.race.addRaceData
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.addSprintQualifyingData
import tmg.flashback.feature.weekend.presentation.data.sprint_race.addSprintRaceData
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh
import tmg.flashback.ui.navigation.NavigationBar
import tmg.flashback.ui.navigation.NavigationItem
import tmg.flashback.ui.navigation.appBarMaximumHeight

@Composable
fun WeekendScreen(
    screenData: WeekendScreenData,
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: WeekendViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    LaunchedEffect(screenData) {
        viewModel.load(
            season = screenData.season,
            round = screenData.round
        )
    }
    WeekendScreenTab(
        isLoading = isLoading.value,
        screenData = screenData,
        paddingValues = paddingValues,
        showBack = showBack,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        uiState = uiState.value,
        clickWeekendTab = viewModel::updateTab,
        refresh = viewModel::refresh
    )
}

@Composable
fun WeekendScreenTab(
    screenData: WeekendScreenData,
    isLoading: Boolean,
    paddingValues: PaddingValues,
    showBack: Boolean,
    actionUpClicked: () -> Unit,
    clickWeekendTab: (WeekendTabs) -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: WeekendUiState,
    refresh: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            isLoading = isLoading,
            onRefresh = refresh,
            content = {


                // Add custom padding for nav bar
                val direction = LocalLayoutDirection.current
                val masterPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
                    start = paddingValues.calculateStartPadding(direction),
                    end = paddingValues.calculateEndPadding(direction)
                )

                LazyColumn(
                    contentPadding = masterPadding,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item("header") {
                        Header(
                            actionUpClicked = actionUpClicked,
                            action = HeaderAction.BACK.takeIf { showBack },
                            text = (uiState as? Data)?.info?.raceName ?: screenData.raceName
                        )
                    }

                    if (uiState is Data) {

                        addDetails(uiState.info)
                        addSchedule(uiState.info)

                        if (uiState.tab == WeekendTabs.Qualifying) {
                            addQualifyingData(
                                uiState
                            )
                        }
                        if (uiState.tab == WeekendTabs.Race) {
                            addRaceData(
                                uiState
                            )
                        }
                        if (uiState.tab == WeekendTabs.SprintQualifying) {
                            addSprintQualifyingData(
                                uiState
                            )
                        }
                        if (uiState.tab == WeekendTabs.SprintRace) {
                            addSprintRaceData(
                                uiState
                            )
                        }
                    }
                }
            }
        )
        if (uiState is Data) {
            val navigationItems = uiState.tabs.toNavigationItem(uiState.tab)
            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                list = navigationItems,
                itemClicked = {
                    val tab = it.id.toEnum<WeekendTabs> { it.id }
                    if (tab != null) {
                        clickWeekendTab(tab)
                    }
                },
                bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
        }
    }
}

private fun List<WeekendTabs>.toNavigationItem(selected: WeekendTabs): List<NavigationItem> {
    return this.map {
        NavigationItem(
            id = it.id,
            label = it.label,
            icon = it.icon,
            isSelected = selected == it
        )
    }
}

fun LazyListScope.addDetails(info: InfoModel) {
    item("details") {
        RaceDetails(
            model = info,
            modifier = Modifier.padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
        )
    }
}

fun LazyListScope.addSchedule(info: InfoModel) {
    item("schedule") {
        Schedule(
            model = info
        )
    }
}
