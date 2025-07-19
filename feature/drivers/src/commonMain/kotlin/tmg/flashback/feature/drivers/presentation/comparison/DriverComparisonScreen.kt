package tmg.flashback.feature.drivers.presentation.comparison

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import flashback.feature.drivers.generated.resources.Res
import flashback.feature.drivers.generated.resources.ic_driver_swap
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.driver_comparison_no_races_in_common
import flashback.presentation.localisation.generated.resources.driver_comparison_stat_dnfs
import flashback.presentation.localisation.generated.resources.driver_comparison_stat_podiums
import flashback.presentation.localisation.generated.resources.driver_comparison_stat_points
import flashback.presentation.localisation.generated.resources.driver_comparison_stat_points_finishes
import flashback.presentation.localisation.generated.resources.driver_comparison_stat_qualifying
import flashback.presentation.localisation.generated.resources.driver_comparison_stat_races
import flashback.presentation.localisation.generated.resources.driver_comparison_stat_wins
import flashback.presentation.localisation.generated.resources.driver_comparison_swap_drivers
import flashback.presentation.localisation.generated.resources.driver_comparison_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.drivers.presentation.shared.DriverBadges
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.driver.DriverIcon
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.progressbar.ProgressBar
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

private val headerImageSize: Dp = 120.dp

@Composable
fun DriverComparisonScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    season: Int,
    viewModel: DriverComparisonViewModel = koinViewModel()
) {
    ScreenView(
        screenName = "Driver Comparison", args = mapOf(
            analyticsSeason to season.toString()
        )
    )

    LaunchedEffect(season) {
        viewModel.setup(season)
    }

    val state = viewModel.uiState.collectAsState()

    SwipeRefresh(
        isLoading = state.value.isLoading,
        onRefresh = viewModel::refresh
    ) {
        DriverComparisonScreen(
            paddingValues = paddingValues,
            actionUpClicked = actionUpClicked,
            windowSizeClass = windowSizeClass,
            season = season,
            driverList = state.value.driverList,
            driverLeft = state.value.driverLeft,
            driverRight = state.value.driverRight,
            selectDriverLeft = viewModel::selectDriverLeft,
            selectDriverRight = viewModel::selectDriverRight,
            swapDriver = viewModel::swapDrivers,
            comparison = state.value.comparison
        )
    }
}

@Composable
private fun DriverComparisonScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    season: Int,
    driverList: List<Driver>,
    driverLeft: Driver?,
    driverRight: Driver?,
    selectDriverLeft: (String) -> Unit,
    selectDriverRight: (String) -> Unit,
    swapDriver: () -> Unit,
    comparison: Comparison?
) {
    LazyColumn(
        contentPadding = paddingValues,
        content = {
            item("header") {
                Header(
                    text = season.toString() + "\n" + stringResource(string.driver_comparison_title),
                    action = when (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED) {
                        true -> HeaderAction.BACK
                        false -> null
                    },
                    actionUpClicked = actionUpClicked
                )
            }
            item("driver-select") {
                val driverLeftExpanded = remember { mutableStateOf(false) }
                val driverRightExpanded = remember { mutableStateOf(false) }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.medium)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        ButtonSecondary(
                            modifier = Modifier.fillMaxWidth(),
                            text = driverLeft?.name ?: "...",
                            onClick = {
                                driverLeftExpanded.value = true
                            }
                        )
                        DriverList(
                            driverList = driverList,
                            expanded = driverLeftExpanded,
                            driverClicked = { selectDriverLeft(it.id) }
                        )
                        if (driverLeft != null && driverRight != null) {
                            DriverIcon(
                                modifier = Modifier.align(Alignment.Start),
                                photoUrl = driverLeft.photoUrl,
                                size = headerImageSize
                            )
                            DriverBadges(
                                modifier = Modifier.padding(vertical = AppTheme.dimens.small),
                                driver = driverLeft,
                                constructors = comparison?.leftConstructors ?: emptyList()
                            )
                        }
                    }
                    IconButton(
                        onClick = swapDriver,
                        modifier = Modifier.padding(horizontal = AppTheme.dimens.small)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_driver_swap),
                            contentDescription = stringResource(string.driver_comparison_swap_drivers),
                            tint = AppTheme.colors.onSurface
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        ButtonSecondary(
                            modifier = Modifier.fillMaxWidth(),
                            text = driverRight?.name ?: "...",
                            onClick = {
                                driverRightExpanded.value = true
                            }
                        )
                        DriverList(
                            driverList = driverList,
                            expanded = driverRightExpanded,
                            driverClicked = { selectDriverRight(it.id) }
                        )
                        if (driverLeft != null && driverRight != null) {
                            DriverIcon(
                                modifier = Modifier.align(Alignment.End),
                                photoUrl = driverRight.photoUrl,
                                size = headerImageSize
                            )
                            DriverBadges(
                                modifier = Modifier.padding(vertical = AppTheme.dimens.small),
                                driver = driverRight,
                                alignment = Alignment.End,
                                constructors = comparison?.rightConstructors ?: emptyList()
                            )
                        }
                    }
                }
            }
            if (comparison != null) {
                item("comparison-races") {
                    ItemComparison(
                        label = stringResource(string.driver_comparison_stat_races),
                        comparison = comparison,
                        percentageResolver = { it.racesHeadToHead.toFloat() },
                        valueResolver = { it.racesHeadToHead.toString() },
                    )
                }
                item("comparison-qualifying") {
                    ItemComparison(
                        label = stringResource(string.driver_comparison_stat_qualifying),
                        comparison = comparison,
                        percentageResolver = { it.qualifyingHeadToHead.toFloat() },
                        valueResolver = { it.qualifyingHeadToHead.toString() },
                    )
                }
                item("comparison-points") {
                    ItemComparison(
                        label = stringResource(string.driver_comparison_stat_points),
                        comparison = comparison,
                        percentageResolver = { it.points?.toFloat() ?: 0f },
                        valueResolver = { it.points?.roundToHalf() ?: "" },
                    )
                }
                item("comparison-points-finishes") {
                    ItemComparison(
                        label = stringResource(string.driver_comparison_stat_points_finishes),
                        comparison = comparison,
                        percentageResolver = { it.pointsFinishes.toFloat() },
                        valueResolver = { it.pointsFinishes.toString() },
                    )
                }
                item("comparison-podiums") {
                    ItemComparison(
                        label = stringResource(string.driver_comparison_stat_podiums),
                        comparison = comparison,
                        percentageResolver = { it.podiums.toFloat() },
                        valueResolver = { it.podiums.toString() },
                    )
                }
                item("comparison-wins") {
                    ItemComparison(
                        label = stringResource(string.driver_comparison_stat_wins),
                        comparison = comparison,
                        percentageResolver = { it.wins.toFloat() },
                        valueResolver = { it.wins.toString() },
                    )
                }
                item("comparison-dnfs") {
                    ItemComparison(
                        label = stringResource(string.driver_comparison_stat_dnfs),
                        comparison = comparison,
                        percentageResolver = { it.dnfs.toFloat() },
                        valueResolver = { it.dnfs.toString() },
                    )
                }
            } else {
                if (driverLeft != null && driverRight != null) {
                    item("no-common-races") {
                        TextBody1(
                            text = stringResource(string.driver_comparison_no_races_in_common),
                            modifier = Modifier.padding(
                                horizontal = AppTheme.dimens.medium,
                                vertical = AppTheme.dimens.medium
                            )
                        )
                    }
                } else {
                    item("get-started") {
                        // TODO: Put something here
                    }
                }
            }
            item("footer") {
                Spacer(Modifier.height(AppTheme.dimens.large))
            }
        }
    )
}

@Composable
private fun ItemComparison(
    label: String,
    comparison: Comparison,
    percentageResolver: (ComparisonValue) -> Float,
    valueResolver: (ComparisonValue) -> String,
    modifier: Modifier = Modifier,
) {
    val (leftPercentage, rightPercentage) = comparison.getPercentages(value = percentageResolver)
    val left = valueResolver(comparison.left)
    val leftColour = comparison.leftConstructors.lastOrNull()?.colour ?: Color.Gray
    val right = valueResolver(comparison.right)
    val rightColour = comparison.rightConstructors.lastOrNull()?.colour ?: Color.Gray
    Column(modifier = modifier.padding(top = AppTheme.dimens.small)) {
        TextTitle(
            text = label,
            bold = true,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimens.medium,
                    vertical = AppTheme.dimens.small
                )
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.medium)
        ) {
            Row(Modifier.weight(1f)) {
                ProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    backgroundColor = AppTheme.colors.surfaceContainer3,
                    barColor = leftColour,
                    endProgress = leftPercentage,
                    label = { left },
                    reverse = true,
                )
            }
            Box(Modifier.width(2.dp))
            Row(Modifier.weight(1f)) {
                ProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    backgroundColor = AppTheme.colors.surfaceContainer3,
                    barColor = rightColour,
                    endProgress = rightPercentage,
                    label = { right },
                    reverse = false,
                )
            }
        }
    }
}

@Composable
private fun DriverList(
    driverList: List<Driver>,
    expanded: MutableState<Boolean>,
    driverClicked: (Driver) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        offset = DpOffset(AppTheme.dimens.medium, 0.dp),
        modifier = modifier.background(AppTheme.colors.tertiaryContainer),
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        content = {
            driverList.forEach { driver ->
                DropdownMenuItem(
                    text = {
                        TextTitle(
                            textColor = AppTheme.colors.onTertiaryContainer,
                            text = driver.name,
                            bold = true
                        )
                    },
                    onClick = {
                        driverClicked(driver)
                        expanded.value = false
                    }
                )
            }
        }
    )
}

@Composable
@Preview
private fun PreviewChristmas(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        DriverComparisonScreen(
            actionUpClicked = {},
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            season = 2024,
            driverList = emptyList(),
            driverLeft = Driver.preview(),
            driverRight = Driver.preview(id = "2"),
            selectDriverLeft = { },
            selectDriverRight = { },
            swapDriver = { },
            comparison = fakeComparison,
            paddingValues = PaddingValues.Absolute()
        )
    }
}

private val fakeComparison = Comparison(
    left = ComparisonValue(
        racesHeadToHead = 3,
        qualifyingHeadToHead = 2,
        points = 100.0,
        pointsFinishes = 4,
        podiums = 0,
        wins = 1,
        dnfs = 1,
    ),
    leftConstructors = listOf(),
    right = ComparisonValue(
        racesHeadToHead = 2,
        qualifyingHeadToHead = 10,
        points = 144.0,
        pointsFinishes = 5,
        podiums = 0,
        wins = 1,
        dnfs = 3
    ),
    rightConstructors = listOf()
)