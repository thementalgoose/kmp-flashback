@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.weekend_info_lap_count
import flashback.presentation.localisation.generated.resources.weekend_info_laps
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
import tmg.flashback.style.text.TextBody2
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
fun TrackBreakdownBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    circuitName: String?,
    countryName: String?,
    countryISO: String?,
    laps: String?,
    trackBreakdownInfo: TrackBreakdownInfo,
    modifier: Modifier = Modifier,
    showDrs: Boolean = true,
    showOvertake: Boolean = true,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { showBottomSheet.value = false },
        containerColor = AppTheme.colors.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
            Box(Modifier
                .padding(vertical = AppTheme.dimens.nsmall)
                .clip(RoundedCornerShape(4.dp))
                .background(AppTheme.colors.surfaceInverse)
                .size(width = 50.dp, height = 4.dp)
            )
        },
        content = {
            TrackBreakdownContent(
                circuitName = circuitName,
                countryName = countryName,
                countryISO = countryISO,
                laps = laps,
                modifier = Modifier,
                trackBreakdownInfo = trackBreakdownInfo,
                showDrs = showDrs,
                showOvertake = showOvertake
            )
        }
    )
}

@Composable
private fun TrackBreakdownContent(
    circuitName: String?,
    countryName: String?,
    countryISO: String?,
    laps: String?,
    modifier: Modifier = Modifier,
    trackBreakdownInfo: TrackBreakdownInfo,
    showDrs: Boolean,
    showOvertake: Boolean,
) {
    val hasOvertakeZones = remember(trackBreakdownInfo) { trackBreakdownInfo.pathOvertakeZones.isNotEmpty() }
    val hasDrsZones = remember(trackBreakdownInfo) { trackBreakdownInfo.pathDrsZones.isNotEmpty() }

    val selected = remember { mutableStateOf<ButtonItem?>(hiddenButton) }
    val selection = selected.value?.key?.toEnum<Zones> { it.key }
    val buttons = listOfNotNull(
        hiddenButton,
        drsButton.takeIf { hasDrsZones && showDrs },
        overtakeButton.takeIf { hasOvertakeZones && showOvertake },
    )

    LazyColumn(modifier = modifier
        .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
        .background(AppTheme.colors.surface)
        .padding(AppTheme.dimens.medium)
    ) {
        item("header") {
            Header(
                circuitName = circuitName ?: "",
                countryName = countryName ?: "",
                countryISO = countryISO
            )
        }
        item("breakdown") {
            TrackBreakdown(
                modifier = Modifier.padding(bottom = AppTheme.dimens.medium),
                trackBreakdownInfo = trackBreakdownInfo,
                showDrs = selection == Zones.DRS,
                showOvertake = selection == Zones.OVERTAKE
            )
        }
        if (laps != null) {
            item("laps") {
                Laps(laps)
            }
        }
        item("zones") {
            Buttons(
                buttons = buttons,
                selected = selected.value,
                buttonClicked = {
                    selected.value = it
                }
            )
        }
    }
}

@Composable
private fun Header(
    circuitName: String,
    countryName: String,
    countryISO: String?
) {
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
        if (countryISO != null) {
            Flag(
                modifier = Modifier.size(48.dp),
                iso = countryISO,
                nationality = countryName
            )
        }
    }
    Spacer(Modifier.height(AppTheme.dimens.xsmall))
}

@Composable
private fun Laps(
    laps: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
<<<<<<< Updated upstream
        TextBody1(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.weekend_info_lap_count)
        )
        TextBody2(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.weekend_info_laps, laps)
=======
        if (circuitName != null && countryName != null && countryISO != null) {
            Row(
                modifier = Modifier
                    .background(AppTheme.colors.surface)
                    .fillMaxWidth(),
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
>>>>>>> Stashed changes
        )
        Spacer(Modifier.height(AppTheme.dimens.xsmall))
    }
}

@Composable
private fun Buttons(
    buttons: List<ButtonItem>,
    selected: ButtonItem?,
    buttonClicked: (ButtonItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (buttons.size > 1) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextBody1(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.weekend_track_zones)
            )
            Segments(
                items = buttons,
                selected = selected,
                segmentClicked = buttonClicked,
                showTick = true
            )
            Spacer(Modifier.height(AppTheme.dimens.xsmall))
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TrackBreakdownContent(
            showDrs = true,
            showOvertake = true,
            laps = "51",
            circuitName = "Zandvoort Circuit",
            countryName = "Netherlands",
            countryISO = "NLD",
            trackBreakdownInfo = trackBreakdownInfoZandvoort
        )
    }
}