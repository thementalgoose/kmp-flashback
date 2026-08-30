package tmg.flashback.feature.season.presentation.driver_standings


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.feature.season.generated.resources.Res
import flashback.feature.season.generated.resources.ic_driver_comparison
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.driver_comparison_title
import flashback.presentation.localisation.generated.resources.season_standings_driver
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.season.presentation.shared.ongoing_banner.ResultAsOf
import tmg.flashback.feature.season.presentation.shared.providedby.ProvidedBy
import tmg.flashback.feature.season.presentation.shared.seasonpicker.ResultsSeasonPicker
import tmg.flashback.formula1.model.SeasonDriverStandingSeason
import tmg.flashback.navigation.NavDriver
import tmg.flashback.navigation.NavDriverComparison
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.driver.DriverIcon
import tmg.flashback.ui.components.edgeBar
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.loading.SkeletonViewList
import tmg.flashback.ui.components.progressbar.ProgressBar
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh
import tmg.flashback.ui.navigation.appBarMaximumHeight

@Composable
fun DriverStandingsScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigateTo: (NavKey) -> Unit,
    viewModel: DriverStandingsViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    // Add custom padding for nav bar

    val insets = when (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        true -> paddingValues
        false -> {
            val direction = LocalLayoutDirection.current
            PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
                start = paddingValues.calculateStartPadding(direction),
                end = paddingValues.calculateEndPadding(direction)
            )
        }
    }

    DriverStandingsScreen(
        paddingValues = insets,
        actionUpClicked = actionUpClicked,
        uiState = state.value,
        windowSizeClass = windowSizeClass,
        driverClicked = {
            navigateTo(NavDriver(
                season = it.season,
                id = it.driver.id,
                name = it.driver.name
            ))
        },
        refresh = viewModel::refresh,
        comparisonClicked = {
            navigateTo(NavDriverComparison(season = state.value.season))
        }
    )
}

@Composable
internal fun DriverStandingsScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    uiState: DriverStandingsState,
    driverClicked: (SeasonDriverStandingSeason) -> Unit,
    comparisonClicked: () -> Unit,
    refresh: () -> Unit
) {
    ScreenView(screenName = "Driver Standings", updateKey = uiState.season, args = mapOf(
        analyticsSeason to uiState.season.toString()
    ))

    SwipeRefresh(
        isLoading = uiState.isLoading,
        onRefresh = refresh
    ) {
        LazyColumn(
            contentPadding = paddingValues,
            content = {
                item(key = "header") {
                    Header(
                        modifier = Modifier.animateItem(),
                        content = {
                            ResultsSeasonPicker(subtitle = stringResource(resource = string.season_standings_driver))
                        },
                        action = if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
                            HeaderAction.MENU
                        } else {
                            null
                        },
                        actionUpClicked = actionUpClicked,
                        overrideIcons = {
                            Refresh(onClick = refresh)
                            IconButton(
                                onClick = comparisonClicked,
                                content = {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_driver_comparison),
                                        contentDescription = stringResource(string.driver_comparison_title),
                                        tint = AppTheme.colors.onSurface
                                    )
                                }
                            )
                        }
                    )
                }
                uiState.inProgress?.let { (raceName, round) ->
                    item(key = "banner") {
                        ResultAsOf(
                            modifier = Modifier
                                .animateItem()
                                .padding(
                                    horizontal = AppTheme.dimens.medium,
                                    vertical = AppTheme.dimens.xsmall
                                ),
                            grandPrixName = raceName,
                            round = round
                        )
                    }
                }
                if (uiState.standings.isEmpty()) {
                    if (uiState.isLoading) {
                        item(key = "loading") {
                            SkeletonViewList(modifier = Modifier.animateItem())
                        }
                    }
                }
                items(uiState.standings, key = { "driver=${it.driver.id}" }) {
                    DriverStandings(
                        modifier = Modifier.animateItem(),
                        model = it,
                        itemClicked = driverClicked,
                        maxPoints = uiState.maxPoints
                    )
                }
                item(key = "footer") {
                    ProvidedBy(
                        modifier = Modifier.animateItem()
                    )
                }
            }
        )
    }
}


@Composable
private fun DriverStandings(
    model: SeasonDriverStandingSeason,
    itemClicked: (SeasonDriverStandingSeason) -> Unit,
    maxPoints: Double,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when {
        model.inProgressContent == null && model.championshipPosition == 1 -> AppTheme.colors.f1ChampionshipHighlight
        else -> Transparent
    }
    val constructorColor =
        model.constructors.lastOrNull()?.constructor?.colour ?: AppTheme.colors.tertiaryContainer
    Row(
        modifier = modifier
            .edgeBar(constructorColor)
            .clickable(onClick = {
                itemClicked(model)
            })
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextTitle(
            text = model.championshipPosition?.toString() ?: "-",
            bold = true,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(36.dp)
        )
        DriverIcon(
            photoUrl = model.driver.photoUrl,
            constructorColor = constructorColor,
            size = 40.dp
        )
        Row(
            modifier = Modifier
                .padding(
                    top = AppTheme.dimens.small,
                    start = AppTheme.dimens.small,
                    end = AppTheme.dimens.medium,
                    bottom = AppTheme.dimens.small
                )
                .wrapContentHeight()
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextTitle(
                        text = model.driver.name,
                        bold = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            vertical = AppTheme.dimens.xxsmall,
                        )
                ) {
                    Flag(
                        iso = model.driver.nationalityISO,
                        nationality = model.driver.nationality,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    TextBody2(
                        text = model.constructors.joinToString { it.constructor.name },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(Modifier.width(AppTheme.dimens.small))
            ProgressBar(
                modifier = Modifier
                    .weight(2f)
                    .height(48.dp)
                    .fillMaxHeight(),
                points = model.points,
                maxPoints = maxPoints,
                barColor = model.constructors.lastOrNull()?.constructor?.colour
                    ?: AppTheme.colors.primary,
                backgroundColor = Transparent
            )
        }
    }
}
