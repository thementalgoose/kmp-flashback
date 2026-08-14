package tmg.flashback.feature.circuits.presentation.circuit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.details_link_map
import flashback.presentation.localisation.generated.resources.details_link_wikipedia
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_details_maps
import flashback.presentation.ui.generated.resources.ic_details_wikipedia
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.window.Dialog
import androidx.navigation3.runtime.NavKey
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsCircuitId
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.formula1.enums.TrackBreakdown
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.CircuitHistoryRace
import tmg.flashback.formula1.model.CircuitHistoryRaceResult
import tmg.flashback.formula1.model.Location
import tmg.flashback.formula1.preview.preview
import tmg.flashback.navigation.NavCircuit
import tmg.flashback.navigation.NavWeekend
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.edgeFade
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh
import tmg.flashback.ui.components.track.TrackBreakdown
import tmg.flashback.ui.components.track.TrackBreakdownDialog
import tmg.flashback.ui.components.track.TrackBreakdownInfo

@Composable
fun CircuitScreen(
    data: NavCircuit,
    paddingValues: PaddingValues,
    navigateTo: (NavKey) -> Unit,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: CircuitViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    ScreenView("Circuit", args = mapOf(
        analyticsCircuitId to data.id
    ))

    LaunchedEffect(data) {
        viewModel.load(data.id)
    }

    CircuitScreen(
        data = data,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        showBack = showBack,
        navigateTo = navigateTo,
        refresh = viewModel::refresh,
        uiState = uiState.value,
        clickLink = viewModel::openLink,
        clickMap = viewModel::openMap
    )
}

@Composable
private fun CircuitScreen(
    data: NavCircuit,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    navigateTo: (NavKey) -> Unit,
    windowSizeClass: WindowSizeClass,
    showBack: Boolean,
    uiState: CircuitUiState,
    refresh: () -> Unit,
    clickLink: (String) -> Unit,
    clickMap: (location: Location, name: String) -> Unit,
) {
    SwipeRefresh(
        isLoading = uiState.isLoading,
        onRefresh = refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            item("header") {
                Header(
                    actionUpClicked = actionUpClicked,
                    action = HeaderAction.BACK.takeIf { showBack },
                    text = uiState.circuit?.name ?: data.name,
                    overrideIcons = {
                        Refresh(onClick = refresh)
                    }
                )
            }
            if (uiState.circuit != null && uiState.trackLayout != null) {
                item("details") {
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = AppTheme.dimens.medium
                            )
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
                        ) {
                            TextTitle(text = uiState.circuit.city)
                            TextTitle(text = uiState.circuit.country)
                            Flag(
                                modifier = Modifier.size(48.dp),
                                iso = uiState.circuit.countryISO,
                                nationality = uiState.circuit.country
                            )
                        }
                        val size = Modifier.size(width = 180.dp, height = 108.dp)
                        if (uiState.trackLayout.breakdown != null) {
                            val trackBreakdownInfo = remember(uiState.trackLayout.breakdown) { uiState.trackLayout.breakdown!!.toInfo() }
                            val showDialog = remember { mutableStateOf(false) }
                            TrackBreakdown(
                                trackBreakdownInfo = trackBreakdownInfo,
                                modifier = size
                                    .clickable(onClick = { showDialog.value = true })
                            )
                            if (showDialog.value) {
                                ScreenView(screenName = "Track Breakdown", args = mapOf(
                                    analyticsCircuitId to uiState.circuit.id
                                ))
                                TrackBreakdownDialog(
                                    circuitName = uiState.circuit.name,
                                    countryName = uiState.circuit.country,
                                    countryISO = uiState.circuit.countryISO,
                                    showDialog = showDialog,
                                    trackBreakdownInfo = trackBreakdownInfo
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(uiState.trackLayout.getDefaultIcon()),
                                contentDescription = null,
                                modifier = size,
                                tint = AppTheme.colors.onSurface
                            )
                        }

                    }
                }
            }
            item("links") {
                if (uiState.circuit != null) {
                    CircuitLinks(
                        modifier = Modifier.padding(bottom = AppTheme.dimens.small),
                        model = uiState.circuit,
                        wikipediaClicked = clickLink,
                        mapsClicked = clickMap,
                    )
                }
            }
            items(uiState.races) {
                Event(
                    model = it,
                    navigateTo = navigateTo
                )
            }
        }
    }
}


@Composable
internal fun CircuitLinks(
    model: Circuit,
    wikipediaClicked: (String) -> Unit,
    mapsClicked: (Location, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (model.location != null || model.wikiUrl != null) {
        Row(
            modifier
                .edgeFade()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = AppTheme.dimens.medium),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
        ) {
            if (model.location != null) {
                BadgeView(
                    modifier = Modifier.clickable {
                        mapsClicked(model.location!!, model.name)
                    },
                    label = stringResource(string.details_link_map),
                    icon = Res.drawable.ic_details_maps
                )
            }
            if (model.wikiUrl != null) {
                BadgeView(
                    modifier = Modifier.clickable {
                        wikipediaClicked(model.wikiUrl!!)
                    },
                    label = stringResource(string.details_link_wikipedia),
                    icon = Res.drawable.ic_details_wikipedia
                )
            }
        }
    }
}

@Composable
private fun Event(
    model: CircuitEvent,
    navigateTo: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.small,
                vertical = AppTheme.dimens.xsmall
            )
            .clickable(onClick = {
                navigateTo(NavWeekend(
                    season = model.race.season,
                    round = model.race.round,
                    raceName = model.race.name
                ))
            })
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
            .background(AppTheme.colors.surfaceContainer3)
            .padding(
                horizontal = AppTheme.dimens.small,
                vertical = AppTheme.dimens.small
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextTitle(
                bold = true,
                modifier = Modifier.weight(1f),
                text = "${model.race.season} ${model.race.name}"
            )
            TextBody1(
                bold = true,
                text = "#${model.race.round}"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = AppTheme.dimens.xsmall
                )
                .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                .background(AppTheme.colors.surfaceContainer5)
                .padding(
                    horizontal = AppTheme.dimens.xsmall,
                    vertical = AppTheme.dimens.xsmall
                ),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            if (model.first != null) {
                Result(model.first)
            }
            if (model.second != null) {
                Result(model.second)
            }
            if (model.third != null) {
                Result(model.third)
            }
        }
    }
}

@Composable
private fun Result(
    model: CircuitHistoryRaceResult
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(modifier = Modifier
            .width(6.dp)
            .clip(RoundedCornerShape(2.dp))
            .fillMaxHeight()
            .background(model.constructor.colour))

        TextBody2(
            text = "${model.position} -",
            bold = true
        )

        TextBody2(
            text = "${model.driver.name} (${model.constructor.name})"
        )
    }
}


private fun TrackBreakdown.toInfo() = TrackBreakdownInfo(
    pathWidth = this.pathWidth,
    pathHeight = this.pathHeight,
    pathTrackWidth = this.trackWidth,
    pathS1 = s1,
    pathS2 = s2,
    pathS3 = s3,
    pathStartLine = startLine,
    pathOvertakeZones = straightModeZones,
    pathDrsZones = drsZones
)

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        CircuitScreen(
            data = NavCircuit("id", "Silverstone"),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            navigateTo = { },
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            uiState = CircuitUiState(
                isLoading = false,
                circuit = Circuit.preview(),
                trackLayout = TrackLayout.ALBERT_PARK,
                races = listOf(
                    CircuitEvent(
                        CircuitHistoryRace.preview(),
                        CircuitHistoryRaceResult.preview(1),
                        CircuitHistoryRaceResult.preview(2),
                        CircuitHistoryRaceResult.preview(3),
                    ),
                    CircuitEvent(
                        CircuitHistoryRace.preview(),
                        CircuitHistoryRaceResult.preview(1),
                        CircuitHistoryRaceResult.preview(2),
                        CircuitHistoryRaceResult.preview(3),
                    )
                )
            ),
            refresh = { },
            clickLink = { },
            clickMap = { _, _ -> }
        )
    }
}