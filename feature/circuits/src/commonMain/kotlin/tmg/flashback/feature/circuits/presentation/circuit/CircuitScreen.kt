package tmg.flashback.feature.circuits.presentation.circuit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.circuits.presentation.all.CircuitNavigation
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.CircuitHistoryRace
import tmg.flashback.formula1.model.CircuitHistoryRaceResult
import tmg.flashback.formula1.model.Location
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun CircuitScreen(
    circuitNavigation: CircuitNavigation,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: CircuitViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(circuitNavigation) {
        viewModel.load(circuitNavigation.circuitId)
    }

    CircuitScreen(
        circuitNavigation = circuitNavigation,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        showBack = showBack,
        refresh = viewModel::refresh,
        uiState = uiState.value,
        clickLink = viewModel::openLink,
        clickMap = viewModel::openMap
    )
}

@Composable
private fun CircuitScreen(
    circuitNavigation: CircuitNavigation,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
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
                    text = uiState.circuit?.name ?: circuitNavigation.circuitName,
                    overrideIcons = {
                        if (uiState.circuit != null) {
                            Icons(
                                model = uiState.circuit,
                                wikipediaClicked = clickLink,
                                mapsClicked = clickMap,
                            )
                        }
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
                                modifier = Modifier.size(32.dp),
                                iso = uiState.circuit.countryISO,
                                nationality = uiState.circuit.country
                            )
                        }
                        Icon(
                            painter = painterResource(uiState.trackLayout.getDefaultIcon()),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            tint = AppTheme.colors.onSurface
                        )
                    }
                }
            }
            items(uiState.races) {
                Event(it)
            }
        }
    }
}



@Composable
private fun RowScope.Icons(
    model: Circuit,
    wikipediaClicked: (String) -> Unit,
    mapsClicked: (Location, String) -> Unit
) {
    if (model.wikiUrl != null) {
        IconButton(
            onClick = { wikipediaClicked(model.wikiUrl!!) },
            content = {
                Icon(
                    painter = painterResource(Res.drawable.ic_details_wikipedia),
                    contentDescription = stringResource(string.details_link_wikipedia),
                    tint = AppTheme.colors.onSurfaceVariant
                )
            }
        )
    }
    if (model.location != null) {
        IconButton(
            onClick = {
                mapsClicked(model.location!!, model.name)
            },
            content = {
                Icon(
                    painter = painterResource(Res.drawable.ic_details_maps),
                    contentDescription = stringResource(string.details_link_map),
                    tint = AppTheme.colors.onSurfaceVariant
                )
            }
        )
    }
}


@Composable
private fun Event(
    model: CircuitEvent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.small,
                vertical = AppTheme.dimens.xsmall
            )
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

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        CircuitScreen(
            circuitNavigation = CircuitNavigation("id", "Silverstone"),
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            windowSizeClass = WindowSizeClass.compute(400f, 700f),
            uiState = CircuitUiState(
                isLoading = false,
                circuit = Circuit.preview(),
                trackLayout = TrackLayout.SILVERSTONE,
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