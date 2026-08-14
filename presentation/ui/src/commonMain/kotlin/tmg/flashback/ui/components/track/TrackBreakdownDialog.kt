package tmg.flashback.ui.components.track

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.details_link_laps
import flashback.presentation.localisation.generated.resources.details_link_map
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonItem
import tmg.flashback.style.buttons.Segments
import tmg.flashback.style.preview.PreviewTheme

@Composable
fun TrackBreakdownDialog(
    showDialog: MutableState<Boolean>,
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
                    trackBreakdownInfo = trackBreakdownInfo,
                    modifier = Modifier,
                )
            }
        }
    )
}

private enum class Zones(
    val key: String,
) {
    DRS("drs"),
    OVERTAKE("overtake")
}

@Composable
private fun TrackBreakdownDialog(
    modifier: Modifier = Modifier,
    trackBreakdownInfo: TrackBreakdownInfo,
) {
    val hasOvertakeZones = remember(trackBreakdownInfo) { trackBreakdownInfo.pathOvertakeZones.isNotEmpty() }
    val hasDrsZones = remember(trackBreakdownInfo) { trackBreakdownInfo.pathDrsZones.isNotEmpty() }

    val buttonDrs = ButtonItem(Zones.DRS.key, Res.string.details_link_map)
    val buttonOvertakeZones = ButtonItem(Zones.OVERTAKE.key, Res.string.details_link_laps)

    val selected = remember { mutableStateOf<ButtonItem?>(null) }
    val selection = remember(selected) { selected.value?.key?.toEnum<Zones> { it.key }}
    val buttons = listOfNotNull(
        buttonDrs.takeIf { hasDrsZones },
        buttonOvertakeZones.takeIf { hasOvertakeZones }
    )

    Column(
        modifier = modifier,
    ) {
        TrackBreakdown(
            trackBreakdownInfo = trackBreakdownInfo,
            showDrs = selection == Zones.DRS,
            showOvertake = selection == Zones.OVERTAKE
        )
        if (hasOvertakeZones || hasDrsZones) {
            Spacer(Modifier.height(AppTheme.dimens.medium))
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
            trackBreakdownInfo = trackBreakdownInfoZandvoort
        )
    }
}