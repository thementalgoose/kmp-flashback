package tmg.flashback.feature.drivers.presentation.season

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import flashback.domain.formula1.generated.resources.ic_championship_order
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.stat_history_championships
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsConstructorId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsDriverId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.drivers.presentation.shared.DriverBadges
import tmg.flashback.feature.drivers.presentation.shared.DriverHeader
import tmg.flashback.feature.drivers.presentation.shared.DriverNotFound
import tmg.flashback.feature.drivers.presentation.shared.ResultHeader
import tmg.flashback.feature.drivers.presentation.shared.ResultRace
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistorySeasonRace
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

data class DriverSeasonInfo(
    val season: Int,
    val id: String,
    val name: String,
)

@Composable
fun DriverSeasonScreen(
    driverSeasonInfo: DriverSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: DriverSeasonViewModel = koinViewModel()
) {
    ScreenView(screenName = "Driver Season", args = mapOf(
        analyticsDriverId to driverSeasonInfo.id,
        analyticsSeason to driverSeasonInfo.season.toString()
    ))
    LaunchedEffect(driverSeasonInfo) {
        viewModel.load(driverSeasonInfo.season, driverSeasonInfo.id)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val loading = viewModel.loading.collectAsStateWithLifecycle()

    DriverSeasonScreen(
        driverSeasonInfo = driverSeasonInfo,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        showBack = showBack,
        uiState = uiState.value,
        refresh = viewModel::refresh,
        isLoading = loading.value,
    )
}

@Composable
fun DriverSeasonScreen(
    driverSeasonInfo: DriverSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    isLoading: Boolean,
    uiState: DriverSeasonUiState?,
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
            val driver = when (uiState) {
                is DriverSeasonUiState.Data -> uiState.driver
                is DriverSeasonUiState.NoRaces -> uiState.driver
                else -> null
            }
            item("header") {
                DriverHeader(
                    label = "${driverSeasonInfo.name}\n${driverSeasonInfo.season}",
                    driverImage = driver?.photoUrl,
                    insetPadding = paddingValues,
                    showBack = showBack,
                    colour = (uiState as? DriverSeasonUiState.Data)?.latestConstructor?.colour ?: AppTheme.colors.primary,
                    backClicked = actionUpClicked
                )
            }

            if (uiState is DriverSeasonUiState.Data) {
                item("badges") {
                    DriverBadges(
                        modifier = Modifier.padding(
                            horizontal = AppTheme.dimens.medium,
                            vertical = AppTheme.dimens.small
                        ),
                        driver = uiState.driver,
                        constructors = uiState.constructors
                    )
                }
                items(uiState.stats) {
                    Stat(it)
                }
                item("races_header") {
                    Column {
                        Spacer(Modifier.height(AppTheme.dimens.small))
                        ResultHeader()
                    }
                }
                items(
                    items = uiState.races,
                    key = { "${it.result.raceInfo.season}-${it.result.raceInfo.round}-${it.result.isSprint}}" }
                ) {
                    ResultRace(
                        multipleConstructors = !uiState.isSingleConstructor,
                        model = it,
                        clickResult = { }
                    )
                }
            }
            else if (uiState is DriverSeasonUiState.NoRaces) {
                item("badges") {
                    DriverBadges(
                        modifier = Modifier.padding(
                            horizontal = AppTheme.dimens.medium,
                            vertical = AppTheme.dimens.small,
                        ),
                        driver = uiState.driver,
                        constructors = emptyList()
                    )
                }
                item("no_races") {
                    DriverNotFound()
                }
            }
        }
    }
}

@Composable
private fun Stat(
    model: DriverSeasonStat,
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

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        DriverSeasonScreen(
            driverSeasonInfo = DriverSeasonInfo(2020, "driver", "name"),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            isLoading = false,
            uiState = fakeDriverSeasonUiState,
            refresh = { }
        )
    }
}

private val fakeDriverSeasonUiState = DriverSeasonUiState.Data(
    season = 2020,
    driver = Driver.preview(),
    isInProgress = true,
    stats = listOf(
        DriverSeasonStat(
            string = string.stat_history_championships,
            icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_championship_order,
            value = "x"
        )
    ),
    races = listOf(
        DriverSeasonRace(result = DriverHistorySeasonRace(
            isSprint = false,
            status = RaceStatus.FINISHED,
            finished = 1,
            points = 10.0,
            qualified = 1,
            gridPos = 1,
            constructor = Constructor.preview(),
            raceInfo = RaceInfo.preview()
        ))
    )
)