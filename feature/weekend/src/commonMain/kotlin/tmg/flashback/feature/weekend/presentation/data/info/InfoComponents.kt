package tmg.flashback.feature.weekend.presentation.data.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.details_link_laps
import flashback.presentation.localisation.generated.resources.details_link_map
import flashback.presentation.localisation.generated.resources.details_link_wikipedia
import flashback.presentation.localisation.generated.resources.details_link_youtube
import flashback.presentation.localisation.generated.resources.weekend_race_round
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_details_maps
import flashback.presentation.ui.generated.resources.ic_details_track
import flashback.presentation.ui.generated.resources.ic_details_wikipedia
import flashback.presentation.ui.generated.resources.ic_details_youtube
import kotlinx.datetime.format.MonthNames
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.formula1.enums.TrackBreakdown
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Location
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.datetime.displayDate
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.edgeFade
import tmg.flashback.ui.components.track.TrackBreakdown
import tmg.flashback.ui.components.track.TrackBreakdownDialog
import tmg.flashback.ui.components.track.TrackBreakdownInfo

@Composable
internal fun RaceDetails(
    model: InfoModel,
    modifier: Modifier = Modifier
) {
    val trackLayout = remember(model.circuit.id) { TrackLayout.getTrack(model.circuit.id) }
    val trackIcon = remember(trackLayout) {
        trackLayout?.getIcon(model.season, model.raceName)
    }
    val trackBreakdown = remember(model) {
        trackLayout?.getBreakdown(model.season, model.raceName)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TextTitle(
                text = stringResource(resource = string.weekend_race_round, model.round),
                bold = true,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            TextBody1(
                modifier = Modifier
                    .fillMaxWidth(),
                text = model.circuit.name
            )
            TextBody1(
                modifier = Modifier
                    .fillMaxWidth(),
                text = model.circuit.country
            )
            TextBody2(
                bold = true,
                modifier = Modifier
                    .fillMaxWidth(),
                text = model.date.displayDate(month = MonthNames.ENGLISH_FULL)
            )
        }
        val sizeModifier = Modifier.size(width = 150.dp, height = 90.dp)
        if (trackBreakdown != null) {
            val trackBreakdownInfo = remember(trackBreakdown) { trackBreakdown.toInfo() }
            val showDialog = remember { mutableStateOf(false) }
            TrackBreakdown(
                trackBreakdownInfo = trackBreakdownInfo,
                modifier = Modifier
                    .clickable(onClick = { showDialog.value = true })
                    .then(sizeModifier)
            )
            if (showDialog.value) {
                TrackBreakdownDialog(
                    showDialog = showDialog,
                    circuitName = model.circuit.name,
                    countryName = model.circuit.country,
                    countryISO = model.circuit.countryISO,
                    trackBreakdownInfo = trackBreakdownInfo
                )
            }
        }
        else if (trackIcon != null) {
            Icon(
                painter = painterResource(trackIcon),
                contentDescription = null,
                modifier = sizeModifier,
                tint = AppTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Composable
internal fun RaceLinks(
    model: InfoModel,
    backgroundColor: Color,
    previousRace: OverviewRace?,
    previousRaceClicked: (OverviewRace) -> Unit,
    youtubeClicked: (String) -> Unit,
    wikipediaClicked: (String) -> Unit,
    mapsClicked: (Location, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (model.laps != null || model.youtubeUrl != null || model.circuit.location != null || model.wikipediaUrl != null) {
        Row(
            modifier
                .edgeFade(backgroundColor = backgroundColor)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = AppTheme.dimens.medium),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
        ) {
            if (model.laps != null) {
                BadgeView(
                    label = stringResource(string.details_link_laps, model.laps),
                )
            }
            if (previousRace != null) {
                BadgeView(
                    modifier = Modifier.clickable {
                        previousRaceClicked(previousRace)
                    },
                    icon = Res.drawable.ic_details_track,
                    label = previousRace.season.toString(),
                )
            }
            if (model.youtubeUrl != null) {
                BadgeView(
                    modifier = Modifier.clickable {
                        youtubeClicked(model.youtubeUrl)
                    },
                    label = stringResource(string.details_link_youtube),
                    icon = Res.drawable.ic_details_youtube
                )
            }
            if (model.circuit.location != null) {
                BadgeView(
                    modifier = Modifier.clickable {
                        mapsClicked(model.circuit.location!!, model.circuit.name)
                    },
                    label = stringResource(string.details_link_map),
                    icon = Res.drawable.ic_details_maps
                )
            }
            if (model.wikipediaUrl != null) {
                BadgeView(
                    modifier = Modifier.clickable {
                        wikipediaClicked(model.wikipediaUrl)
                    },
                    label = stringResource(string.details_link_wikipedia),
                    icon = Res.drawable.ic_details_wikipedia
                )
            }
        }
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
private fun PreviewDetailsTrackIcon() {
    ApplicationThemePreview {
        Column {
            RaceDetails(
                model = InfoModel.preview(
                    circuit = Circuit.preview(id = "sebring")
                )
            )
        }
    }
}


@PreviewTheme
@Composable
private fun PreviewDetailsTrackBreakdown() {
    ApplicationThemePreview {
        Column {
            RaceDetails(
                model = InfoModel.preview(
                    circuit = Circuit.preview(id = "albert_park")
                )
            )
        }
    }
}