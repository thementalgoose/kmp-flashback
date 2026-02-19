package tmg.flashback.feature.drivers.presentation.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.dashboard_all_title
import flashback.presentation.ui.generated.resources.ic_menu_drivers
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsDriverId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.drivers.presentation.shared.DriverBadges
import tmg.flashback.feature.drivers.presentation.shared.DriverHeader
import tmg.flashback.feature.drivers.presentation.shared.DriverTeam
import tmg.flashback.feature.drivers.presentation.shared.ResultHeader
import tmg.flashback.feature.drivers.presentation.shared.ResultRace
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.navigation.NavDriver
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.season.PickerItem
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun DriverStatsScreen(
    data: NavDriver,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: DriverStatsViewModel = koinViewModel(),
) {
    LaunchedEffect(data) {
        viewModel.loadDriver(data.id, data.season)
    }

    val isLoading = viewModel.loading.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    val currentSeason = when (val selection = uiState.value.selection) {
        DriverFilter.Overview -> "All"
        is DriverFilter.Season -> selection.season.toString()
        null -> data.season?.toString() ?: "All"
    }
    ScreenView(screenName = "Driver Season", updateKey = currentSeason, args = mapOf(
        analyticsDriverId to data.id,
        analyticsSeason to currentSeason
    ))

    DriverStatsScreen(
        data = data,
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
    data: NavDriver,
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
        val option = remember(uiState.selection) {
            when (uiState.selection) {
                DriverFilter.Overview -> PickerItem.Label(stringRes = string.dashboard_all_title)
                is DriverFilter.Season -> PickerItem.Text(uiState.selection.season.toString())
                null -> null
            }
        }
        val optionsToShow = remember(uiState.availableSeasons) {
            listOf(PickerItem.Label(string.dashboard_all_title)) +
                    uiState.availableSeasons.map { PickerItem.Text(it.toString()) }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = bottomOnlyPadding
        ) {
            item("header") {
                DriverHeader(
                    modifier = Modifier.animateItem(),
                    driverName = uiState.driver?.name ?: data.name,
                    driverImage = uiState.driver?.photoUrl,
                    option = option ?: PickerItem.Text("..."),
                    optionsToShow = optionsToShow,
                    optionClicked = {
                        when (it) {
                            is PickerItem.Label -> changeSelection(DriverFilter.Overview)
                            is PickerItem.Text -> changeSelection(DriverFilter.Season(it.text.toInt()))
                        }
                    },
                    colour = uiState.constructors.firstOrNull()?.colour ?: AppTheme.colors.primary,
                    showBack = showBack,
                    insetPadding = paddingValues,
                    backClicked = actionUpClicked,
                    overrideIcons = {
                        Refresh(onClick = refresh)
                    }
                )
            }
            if (uiState.driver != null) {
                item("badges") {
                    DriverBadges(
                        modifier = Modifier
                            .animateItem()
                            .padding(
                                horizontal = AppTheme.dimens.medium,
                                vertical = AppTheme.dimens.small
                            ),
                        driver = uiState.driver,
                        constructors = uiState.constructors
                    )
                }
            }
            items(uiState.stats) {
                Stat(
                    model = it,
                    modifier = Modifier.animateItem()
                )
            }
            when (uiState.data) {
                is DriverStatsData.Overview -> {
                    items(uiState.data.teams.toList()) { it ->
                        DriverTeam(
                            modifier = Modifier.animateItem(),
                            year = it.season,
                            constructors = it.teams,
                            standing = it.standing,
                            yearClicked = {
                                changeSelection(DriverFilter.Season(it))
                            }
                        )
                    }
                }
                is DriverStatsData.Season -> {
                    item("races_header") {
                        Column(
                            modifier = Modifier.animateItem()
                        ) {
                            Spacer(Modifier.height(AppTheme.dimens.small))
                            ResultHeader()
                        }
                    }
                    items(
                        items = uiState.data.races,
                        key = { "${it.raceInfo.season}-${it.raceInfo.round}-${it.isSprint}}" }
                    ) {
                        ResultRace(
                            modifier = Modifier.animateItem(),
                            multipleConstructors = uiState.constructors.size > 1,
                            model = it,
                            clickResult = { }
                        )
                    }
                }
                null -> {

                }
            }
        }
    }
}


@Composable
private fun Stat(
    model: DriverStat,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.xsmall
        ),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(16.dp),
            painter = painterResource(model.icon),
            contentDescription = null,
            tint = AppTheme.colors.onSurface
        )
        TextBody2(
            modifier = Modifier.weight(1f),
            text = stringResource(model.string),
        )
        TextBody1(
            bold = true,
            text = model.value,
        )
    }
}

@Preview
@Composable
private fun PreviewAll(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        DriverStatsScreen(
            data = NavDriver(20202, "driver", "name"),
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            isLoading = false,
            refresh = { },
            uiState = DriverStatsUiState(
                driver = Driver.preview(),
                selection = DriverFilter.Overview,
                availableSeasons = listOf(2020, 2019),
                stats = listOf(DriverStat(string.dashboard_all_title, flashback.presentation.ui.generated.resources.Res.drawable.ic_menu_drivers, "value")),
                data = DriverStatsData.Overview(
                    teams = listOf(
                        DriverStatTeamOverview(season = 2020, teams = listOf(Constructor.preview()), standing = 2),
                        DriverStatTeamOverview(season = 2021, teams = listOf(Constructor.preview()), standing = 1)
                    )
                )
            ),
            changeSelection = { }
        )
    }
}
@Preview
@Composable
private fun PreviewSeason(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        DriverStatsScreen(
            data = NavDriver(20202, "driver", "name"),
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            isLoading = false,
            refresh = { },
            uiState = DriverStatsUiState(
                driver = Driver.preview(),
                selection = DriverFilter.Season(2020),
                availableSeasons = listOf(2020, 2019),
                stats = listOf(DriverStat(string.dashboard_all_title, flashback.presentation.ui.generated.resources.Res.drawable.ic_menu_drivers, "value")),
                data = DriverStatsData.Season(
                    races = listOf()
                )
            ),
            changeSelection = { }
        )
    }
}