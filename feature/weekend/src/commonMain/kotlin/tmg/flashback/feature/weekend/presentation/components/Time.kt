package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_result_finish_dnf
import flashback.presentation.localisation.generated.resources.ab_result_finish_time
import flashback.presentation.localisation.generated.resources.race_status_retired
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.enums.isStatusFinished
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextCaption

internal val timeWidth = 70.dp

@Composable
internal fun Time(
    lapTime: LapTime?,
    status: RaceStatus,
    modifier: Modifier = Modifier
) {
    val contentDescription = when {
        lapTime?.noTime == false -> stringResource(resource = string.ab_result_finish_time, "+${lapTime.contentDescription}")
        status.isStatusFinished() -> status.label
        else -> stringResource(resource = string.ab_result_finish_dnf, status.label)
    }
    TextCaption(
        modifier = modifier
            .semantics(mergeDescendants = true) { }
            .clearAndSetSemantics {
                this.contentDescription = contentDescription
            },
        textAlign = TextAlign.Center,
        maxLines = 2,
        text = when {
            lapTime?.noTime == false -> "+${lapTime.time}"
            status.isStatusFinished() -> status.label
            else -> "${stringResource(resource = string.race_status_retired)}\n${status.label}"
        },
    )
}

@Preview
@Composable
private fun Preview() {
    AppThemePreview {
        Column {
            Time(LapTime.noTime, RaceStatus.FINISHED)
            Time(LapTime(1, 1, 3, 4), RaceStatus.FINISHED)
            Time(LapTime.noTime, RaceStatus.WITHDREW)
            Time(LapTime.noTime, RaceStatus.ACCIDENT)
        }
    }
}