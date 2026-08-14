package tmg.flashback.ui.components.track

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.ab_hide
import flashback.presentation.localisation.generated.resources.details_link_laps
import flashback.presentation.localisation.generated.resources.details_link_map
import flashback.presentation.localisation.generated.resources.weekend_track_zones
import flashback.presentation.localisation.generated.resources.weekend_track_zones_drs
import flashback.presentation.localisation.generated.resources.weekend_track_zones_hidden
import flashback.presentation.localisation.generated.resources.weekend_track_zones_overtake
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonItem
import tmg.flashback.style.buttons.Segments
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.flag.Flag

private enum class Zones(
    val key: String
) {
    DRS("drs"),
    OVERTAKE("overtake")
}
private val hiddenButton = ButtonItem(
    key = "none",
    string = Res.string.weekend_track_zones_hidden
)
private val drsButton = ButtonItem(
    key = Zones.DRS.key,
    string = Res.string.weekend_track_zones_drs
)
private val overtakeButton = ButtonItem(
    key = Zones.OVERTAKE.key,
    string = Res.string.weekend_track_zones_overtake
)

@Composable
fun TrackBreakdownDialog(
    showDialog: MutableState<Boolean>,
    circuitName: String?,
    countryName: String?,
    countryISO: String?,
    trackBreakdownInfo: TrackBreakdownInfo,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = { showDialog.value = false },
        content = {
            Box(modifier = modifier
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(AppTheme.colors.surface)
                .padding(AppTheme.dimens.medium)
            ) {
                TrackBreakdownDialog(
                    circuitName = circuitName,
                    countryName = countryName,
                    countryISO = countryISO,
                    trackBreakdownInfo = trackBreakdownInfo,
                    modifier = Modifier,
                )
            }
        }
    )
}

@Composable
private fun TrackBreakdownDialog(
    circuitName: String?,
    countryName: String?,
    countryISO: String?,
    modifier: Modifier = Modifier,
    trackBreakdownInfo: TrackBreakdownInfo,
) {
    val hasOvertakeZones = remember(trackBreakdownInfo) { trackBreakdownInfo.pathOvertakeZones.isNotEmpty() }
    val hasDrsZones = remember(trackBreakdownInfo) { trackBreakdownInfo.pathDrsZones.isNotEmpty() }

    val selected = remember { mutableStateOf<ButtonItem?>(hiddenButton) }
    val selection = selected.value?.key?.toEnum<Zones> { it.key }

    val buttons = listOfNotNull(
        hiddenButton.takeIf { !hasOvertakeZones || !hasDrsZones},
        drsButton.takeIf { hasDrsZones },
        overtakeButton.takeIf { hasOvertakeZones },
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        if (circuitName != null && countryName != null && countryISO != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
                ) {
                    TextTitle(
                        text = circuitName,
                        bold = true
                    )
                    TextBody1(
                        text = countryName,
                        bold = false
                    )
                }
                Flag(
                    modifier = Modifier.size(48.dp),
                    iso = countryISO,
                    nationality = countryName
                )
            }
            Spacer(Modifier.height(AppTheme.dimens.xsmall))
        }
        TrackBreakdown(
            trackBreakdownInfo = trackBreakdownInfo,
            showDrs = selection == Zones.DRS,
            showOvertake = selection == Zones.OVERTAKE
        )
        if (hasOvertakeZones || hasDrsZones) {
            Spacer(Modifier.height(AppTheme.dimens.xsmall))
            TextBody1(
                text = stringResource(Res.string.weekend_track_zones)
            )
            Segments(
                items = buttons,
                selected = selected.value,
                segmentClicked = {
                    selected.value = it
                },
                showTick = true
            )
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TrackBreakdownDialog(
            circuitName = "Zandvoort Circuit",
            countryName = "Netherlands",
            countryISO = "NLD",
            trackBreakdownInfo = trackBreakdownInfoZandvoort
        )
    }
}