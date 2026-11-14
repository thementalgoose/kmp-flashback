package tmg.flashback.ui.components.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.countdown_days
import flashback.presentation.localisation.generated.resources.countdown_hours
import flashback.presentation.localisation.generated.resources.countdown_minutes
import flashback.presentation.localisation.generated.resources.countdown_seconds
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextCaption
import tmg.flashback.style.text.TextTitle

@Composable
internal fun RelativeTimer(
    days: Long,
    hours: Long,
    minutes: Long,
    seconds: Long,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TimerCard(
            modifier = Modifier.weight(1f),
            value = days.toString(),
            label = stringResource(string.countdown_days),
        )
        TextTitle(":")
        TimerCard(
            modifier = Modifier.weight(1f),
            value = hours.toString(),
            label = stringResource(string.countdown_hours),
        )
        TextTitle(":")
        TimerCard(
            modifier = Modifier.weight(1f),
            value = minutes.toString(),
            label = stringResource(string.countdown_minutes),
        )
        TextTitle(":")
        TimerCard(
            modifier = Modifier.weight(1f),
            value = seconds.toString(),
            label = stringResource(string.countdown_seconds),
        )
    }
}

@Composable
private fun TimerCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                .background(AppTheme.colors.surfaceContainer5)
                .padding(
                    horizontal = AppTheme.dimens.medium,
                    vertical = AppTheme.dimens.small
                )
        ) {
            TextTitle(
                text = value.padStart(2, padChar = '0'),
                modifier = Modifier.fillMaxWidth(),
                mono = true,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
            TextCaption(
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                text = label.uppercase(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        RelativeTimer(
            days = 5,
            hours = 4,
            minutes = 3,
            seconds = 2,
        )
    }
}