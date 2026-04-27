package tmg.flashback.widgets.upnext.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.components.WidgetTitle
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.preview.fakeSprintWeekend
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.marginMedium
import tmg.flashback.widgets.upnext.presentation.style.marginNSmall
import tmg.flashback.widgets.upnext.presentation.style.marginSmall
import tmg.flashback.widgets.upnext.presentation.style.marginXSmall
import tmg.flashback.widgets.upnext.presentation.style.text.TextFeature
import tmg.flashback.widgets.upnext.utils.labels

internal const val raceNameWidth = 200
internal const val raceNameHeight = 80
private const val raceNameHeightBreakpoint = 105
private const val raceNameWidthBreakpoint = 280

@Composable
internal fun RaceName(
    overviewRace: OverviewRace,
    localSize: DpSize,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(modifier = modifier) {
        WidgetTitle(
            overviewRace = overviewRace,
            showCircuit = localSize.height >= raceNameHeightBreakpoint.dp,
            titleMaxLines = 1,
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(
                    top = marginNSmall,
                    start = marginNSmall,
                    end = marginNSmall
                )
        )
        val (date, time) = overviewRace.labels(shortWeek = localSize.width < raceNameWidthBreakpoint.dp)
        Row(
            modifier = GlanceModifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFeature(
                text = "$date ($time)",
                maxLines = 1,
                modifier = GlanceModifier
                    .padding(horizontal = marginMedium)
            )
        }
    }
}

@ExperimentalGlancePreviewApi
@Preview(widthDp = raceNameWidth, heightDp = raceNameHeight)
@Composable
private fun PreviewOveview() {
    WidgetThemePreview {
        RaceName(
            overviewRace = fakeOverviewRace,
            localSize = LocalSize.current
        )
    }
}

@ExperimentalGlancePreviewApi
@Preview(widthDp = raceNameWidth, heightDp = raceNameHeight)
@Composable
private fun PreviewSprint() {
    WidgetThemePreview {
        RaceName(
            overviewRace = fakeSprintWeekend,
            localSize = LocalSize.current
        )
    }
}