package tmg.flashback.widgets.upnext.presentation.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import kotlinx.datetime.LocalDateTime
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.utils.labels
import tmg.flashback.widgets.upnext.utils.raceSchedule

@Composable
internal fun Schedule(
    model: Schedule,
    context: Context,
    compressed: Boolean,
    modifier: GlanceModifier = GlanceModifier
) {
    val deviceLocaleTime = model.timestamp.deviceLocalDateTime
    val alpha = if (deviceLocaleTime < LocalDateTime.now()) 0.5f else 1f
    val (day, time) = model.labels()
    Row(modifier = modifier.padding(top = 3.dp)) {
        TextBody(
            modifier = GlanceModifier,
            color = GlanceTheme.colors.onBackground.getColor(context).copy(alpha = alpha),
            text = if (compressed) model.label.shortenLabel() else model.label,
            weight = FontWeight.Bold
        )
        Spacer(GlanceModifier.width(12.dp))
        TextBody(
            color = GlanceTheme.colors.onBackground.getColor(context).copy(alpha = alpha),
            text = "$day (${time})"
        )
    }
}

private fun String.shortenLabel() = when (this.lowercase()) {
    "sprint qualifying" -> "Sprint Q.."
    "sprint shootout" -> "Sprint Q.."
    "qualifying" -> "Quali.."
    "qualify" -> "Quali.."
    else -> this
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun PreviewCompressed() {
    WidgetThemePreview {
        Schedule(
            context = LocalContext.current,
            model = fakeOverviewRace.raceSchedule()!!,
            compressed = true
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun PreviewExpanded() {
    WidgetThemePreview {
        Schedule(
            context = LocalContext.current,
            model = fakeOverviewRace.raceSchedule()!!,
            compressed = false
        )
    }
}