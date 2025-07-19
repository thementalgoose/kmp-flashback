package tmg.flashback.feature.constructors.presentation.season

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import flashback.domain.formula1.generated.resources.ic_championship_order
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_championship_standing
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points_finishes
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_qualifying_poles
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_podiums
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_wins
import flashback.presentation.localisation.generated.resources.stat_history_championships
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsConstructorId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.constructors.presentation.shared.ConstructorHeader
import tmg.flashback.feature.drivers.presentation.shared.ConstructorNotFound
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.driver.DriverIcon
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh


data class ConstructorSeasonInfo(
    val season: Int,
    val id: String,
    val name: String,
)

@Composable
fun ConstructorSeasonScreen(
    constructorSeasonInfo: ConstructorSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: ConstructorSeasonViewModel = koinViewModel()
) {
    ScreenView(screenName = "Constructor Season", args = mapOf(
        analyticsConstructorId to constructorSeasonInfo.id,
        analyticsSeason to constructorSeasonInfo.season.toString()
    ))

    LaunchedEffect(constructorSeasonInfo) {
        viewModel.load(constructorSeasonInfo.season, constructorSeasonInfo.id)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val loading = viewModel.loading.collectAsStateWithLifecycle()

    ConstructorSeasonScreen(
        constructorSeasonInfo = constructorSeasonInfo,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        showBack = showBack,
        uiState = uiState.value,
        refresh = viewModel::refresh,
        isLoading = loading.value,
    )
}


@Composable
fun ConstructorSeasonScreen(
    constructorSeasonInfo: ConstructorSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    isLoading: Boolean,
    uiState: ConstructorSeasonUiState?,
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = bottomOnlyPadding
        ) {
            val constructor = when (uiState) {
                is ConstructorSeasonUiState.Data -> uiState.constructor
                is ConstructorSeasonUiState.NoRaces -> uiState.constructor
                else -> null
            }
            item("header") {
                ConstructorHeader(
                    label = "${constructorSeasonInfo.name}\n${constructorSeasonInfo.season}",
                    constructorImage = constructor?.photoUrl,
                    insetPadding = paddingValues,
                    showBack = showBack,
                    colour = constructor?.colour ?: AppTheme.colors.primary,
                    backClicked = actionUpClicked
                )
            }
            if (uiState is ConstructorSeasonUiState.Data) {
                item("spacer") {
                    Spacer(Modifier.height(AppTheme.dimens.medium))
                }
                items(uiState.stats) {
                    Stat(it)
                }
                items(uiState.drivers) {
                    Driver(it)
                }
            }
            else if (uiState is ConstructorSeasonUiState.NoRaces) {
                item("no_races") {
                    ConstructorNotFound()
                }
            }
        }
    }
}


@Composable
private fun Stat(
    model: ConstructorSeasonStat,
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
                .background(AppTheme.colors.surfaceContainer4)
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

private val driverImageSize = 64.dp

@Composable
private fun Driver(
    model: ConstructorSeasonDriver,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .height(IntrinsicSize.Min)
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.small
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Max)
                .padding(start = driverImageSize / 2)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(AppTheme.colors.surfaceContainer3)
                .padding(
                    start = (driverImageSize / 2) + AppTheme.dimens.nsmall,
                    end = AppTheme.dimens.medium,
                    top = AppTheme.dimens.medium,
                    bottom = AppTheme.dimens.medium
                ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
        ) {
            TextTitle(
                text = model.result.driver.driver.name,
                bold = true
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp)
            )
            DriverStat(
                label = string.constructor_overview_stat_championship_standing,
                value = model.result.championshipStanding?.ordinalAbbreviation ?: "-"
            )
            DriverStat(
                label = string.constructor_overview_stat_race_wins,
                value = model.result.wins.toString()
            )
            DriverStat(
                label = string.constructor_overview_stat_race_podiums,
                value = model.result.podiums.toString()
            )
            DriverStat(
                label = string.constructor_overview_stat_qualifying_poles,
                value = model.result.polePosition.toString()
            )
            DriverStat(
                label = string.constructor_overview_stat_points,
                value = model.result.points.roundToHalf()
            )
            DriverStat(
                label = string.constructor_overview_stat_points_finishes,
                value = model.result.finishesInPoints.toString()
            )
        }
        DriverIcon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = AppTheme.dimens.medium),
            photoUrl = model.result.driver.driver.photoUrl,
            size = driverImageSize,
            constructorColor = model.result.driver.constructor.colour
        )
    }
}


@Composable
private fun DriverStat(
    label: StringResource,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        TextBody2(
            text = stringResource(label),
            modifier = Modifier.weight(1f)
        )
        TextBody2(
            text = value,
            bold = true
        )
    }
}


@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        ConstructorSeasonScreen(
            constructorSeasonInfo = ConstructorSeasonInfo(2020, "id", "name"),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            isLoading = false,
            uiState = fakeConstructorSeasonUiState,
            refresh = { }
        )
    }
}

private val fakeConstructorSeasonUiState = ConstructorSeasonUiState.Data(
    season = 2020,
    constructor = Constructor.preview(),
    isInProgress = true,
    stats = listOf(
        ConstructorSeasonStat(
            string = string.stat_history_championships,
            icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_championship_order,
            value = "x"
        )
    ),
    drivers = listOf(
        ConstructorSeasonDriver(result = ConstructorHistorySeasonDriver(
            driver = DriverEntry.preview(),
            points = 2.0,
            wins = 1,
            races = 3,
            podiums = 4,
            finishesInPoints = 10,
            polePosition = 2,
            championshipStanding = 2
        ))
    )
)