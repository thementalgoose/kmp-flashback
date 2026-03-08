package tmg.flashback.feature.constructors.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.dashboard_all_title
import flashback.presentation.ui.generated.resources.ic_menu_drivers
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsConstructorId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.constructors.presentation.shared.ConstructorDriver
import tmg.flashback.feature.constructors.presentation.shared.ConstructorHeader
import tmg.flashback.feature.constructors.presentation.shared.ConstructorSeason
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.preview.preview
import tmg.flashback.navigation.NavTeam
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.season.PickerItem
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun ConstructorStatsScreen(
    data: NavTeam,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: ConstructorStatsViewModel = koinViewModel()
) {
    LaunchedEffect(data) {
        viewModel.loadConstructor(data.id, data.season)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val loading = viewModel.loading.collectAsStateWithLifecycle()

    val currentSeason = when (val selection = uiState.value.selection) {
        ConstructorFilter.Overview -> "All"
        is ConstructorFilter.Season -> selection.season.toString()
        null -> data.season.toString()
    }
    ScreenView(screenName = "Constructor Season", updateKey = currentSeason, args = mapOf(
        analyticsConstructorId to data.id,
        analyticsSeason to currentSeason
    ))

    ConstructorStatsScreen(
        data = data,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        showBack = showBack,
        uiState = uiState.value,
        refresh = viewModel::refresh,
        changeSelection = viewModel::changeSelection,
        isLoading = loading.value,
    )
}


@Composable
internal fun ConstructorStatsScreen(
    data: NavTeam,
    windowSizeClass: WindowSizeClass,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    isLoading: Boolean,
    uiState: ConstructorStatsUiState,
    changeSelection: (ConstructorFilter) -> Unit,
    refresh: () -> Unit
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
                ConstructorFilter.Overview -> PickerItem.Label(stringRes = string.dashboard_all_title)
                is ConstructorFilter.Season -> PickerItem.Text(uiState.selection.season.toString())
                null -> null
            }
        }
        val optionsToShow = remember(uiState.availableSeasons) {
            listOf(PickerItem.Label(string.dashboard_all_title)) +
                    uiState.availableSeasons.map { PickerItem.Text(it.toString()) }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = bottomOnlyPadding,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
        ) {
            item("header") {
                ConstructorHeader(
                    modifier = Modifier.animateItem(),
                    constructorName = data.name,
                    option = option ?: PickerItem.Text("..."),
                    optionsToShow = optionsToShow,
                    optionClicked = {
                        when (it) {
                            is PickerItem.Label -> changeSelection(ConstructorFilter.Overview)
                            is PickerItem.Text -> changeSelection(ConstructorFilter.Season(it.text.toInt()))
                        }
                    },
                    constructorImage = uiState.constructor?.photoUrl,
                    insetPadding = paddingValues,
                    showBack = showBack,
                    colour = uiState.constructor?.colour ?: AppTheme.colors.primary,
                    backClicked = actionUpClicked,
                    overrideIcons = {
                        Refresh(onClick = refresh)
                    }
                )
            }
            items(uiState.stats) {
                Stat(
                    model = it,
                    modifier = Modifier.animateItem()
                )
            }
            when (uiState.data) {
                is ConstructorStatsData.Overview -> {
                    items(uiState.data.items.toList()) { model ->
                        ConstructorSeason(
                            modifier = Modifier.animateItem(),
                            year = model.season,
                            yearClicked = {
                                changeSelection(ConstructorFilter.Season(model.season))
                            },
                            drivers = model.drivers,
                            standing = model.standing
                        )
                    }
                }
                is ConstructorStatsData.Season -> {
                    items(uiState.data.drivers) {
                        ConstructorDriver(
                            modifier = Modifier.animateItem(),
                            model = it,
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
    model: ConstructorStat,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.xxsmall
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



@PreviewTheme
@Composable
private fun PreviewAll() {
    ApplicationThemePreview {
        ConstructorStatsScreen(
            data = NavTeam(2020, "driver", "name"),
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            isLoading = false,
            refresh = { },
            uiState = ConstructorStatsUiState(
                constructor = Constructor.preview(),
                selection = ConstructorFilter.Overview,
                availableSeasons = listOf(2020, 2019),
                stats = listOf(ConstructorStat(string.dashboard_all_title, flashback.presentation.ui.generated.resources.Res.drawable.ic_menu_drivers, "value")),
                data = ConstructorStatsData.Overview(
                    items = listOf(
                        ConstructorStatSeasonOverview(
                            season = 2020,
                            drivers = mapOf(1 to Driver.preview()),
                            standing = 1
                        )
                    )
                )
            ),
            changeSelection = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewSeason() {
    ApplicationThemePreview {
        ConstructorStatsScreen(
            data = NavTeam(2020, "driver", "name"),
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            isLoading = false,
            refresh = { },
            uiState = ConstructorStatsUiState(
                constructor = Constructor.preview(),
                selection = ConstructorFilter.Season(2020),
                availableSeasons = listOf(2020, 2019),
                stats = listOf(ConstructorStat(string.dashboard_all_title, flashback.presentation.ui.generated.resources.Res.drawable.ic_menu_drivers, "value")),
                data = ConstructorStatsData.Season(
                    drivers = List(3) {
                        ConstructorHistorySeasonDriver(
                            driver = DriverEntry.preview("driverId$it"),
                            points = 1.0,
                            wins = 1,
                            races = 2,
                            podiums = 3,
                            polePosition = 4,
                            championshipStanding = 1,
                            finishesInPoints = 3
                        )
                    }
                )
            ),
            changeSelection = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewOverview() {
    ApplicationThemePreview {
        ConstructorStatsScreen(
            data = NavTeam(2020, "driver", "name"),
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            isLoading = false,
            refresh = { },
            uiState = ConstructorStatsUiState(
                constructor = Constructor.preview(),
                selection = ConstructorFilter.Season(2020),
                availableSeasons = listOf(2020, 2019),
                stats = listOf(ConstructorStat(string.dashboard_all_title, flashback.presentation.ui.generated.resources.Res.drawable.ic_menu_drivers, "value")),
                data = ConstructorStatsData.Overview(
                    items = listOf(
                        ConstructorStatSeasonOverview(
                            season = 2020,
                            drivers = mapOf(
                                1 to Driver.preview(id = "driverId1"),
                                2 to Driver.preview(id = "driverId2"),
                            ),
                            standing = 1
                        )
                    )
                )
            ),
            changeSelection = { }
        )
    }
}