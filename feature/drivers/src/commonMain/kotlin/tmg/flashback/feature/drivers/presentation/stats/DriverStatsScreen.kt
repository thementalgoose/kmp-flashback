package tmg.flashback.feature.drivers.presentation.stats

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsDriverId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

data class DriverStatsInfo(
    val driverId: String,
    val driverName: String,
    val season: Int? = null
)

@Composable
fun DriverStatsScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    driverStats: DriverStatsInfo,
    viewModel: DriverStatsViewModel = koinViewModel(),
) {
    ScreenView(screenName = "Driver Season", args = mapOf(
        analyticsDriverId to driverStats.driverId,
        analyticsSeason to (driverStats.season?.toString() ?: "All")
    ))

    LaunchedEffect(driverStats) {
        viewModel.loadDriver(driverStats.driverId, driverStats.season)
    }

    val isLoading = viewModel.loading.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    DriverStatsScreen(
        driverStats = driverStats,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        showBack = showBack,
        windowSizeClass = windowSizeClass,
        isLoading = isLoading.value,
        uiState = uiState.value,
        changeSelection = viewModel::changeSelection,
        refresh = viewModel::refresh,
    )
}

@Composable
private fun DriverStatsScreen(
    driverStats: DriverStatsInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    isLoading: Boolean,
    uiState: DriverStatsUiState,
    changeSelection: (DriverFilter) -> Unit,
    refresh: () -> Unit,
) {
    val direction = LocalLayoutDirection.current
    val bottomOnlyPadding = PaddingValues(
        start = paddingValues.calculateStartPadding(direction),
        end = paddingValues.calculateEndPadding(direction),
        bottom = paddingValues.calculateBottomPadding()
    )
    SwipeRefresh(
        isLoading = isLoading,
        onRefresh = refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = bottomOnlyPadding
        ) {
            item("header") {

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

    }
}