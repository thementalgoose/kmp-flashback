package tmg.flashback.widgets.upnext.presentation.layout

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import androidx.glance.text.TextAlign
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.modifiers.surface
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody
import tmg.flashback.widgets.upnext.presentation.components.CountryIcon
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.utils.labels
import tmg.flashback.widgets.upnext.utils.raceSchedule

private const val raceIconWidth = 80
private const val raceIconHeight = 80

@Composable
internal fun RaceIcon(
    context: Context,
    overviewRace: OverviewRace,
    showText: Boolean,
    modifier: GlanceModifier = GlanceModifier,
) {
    val schedule = overviewRace.raceSchedule()
    Column(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountryIcon(
            context = context,
            country = overviewRace.country,
            countryISO = overviewRace.countryISO
        )
        if (schedule != null && showText) {
            val (day, time) = schedule.labels()
            TextBody(
                modifier = GlanceModifier.padding(
                    top = 2.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
                textAlign = TextAlign.Center,
                text = day,
                weight = FontWeight.Bold,
                color = GlanceTheme.colors.onBackground.getColor(context)
            )
            TextBody(
                modifier = GlanceModifier.padding(
                    top = 2.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
                textAlign = TextAlign.Center,
                text = time,
                color = GlanceTheme.colors.onBackground.getColor(context)
            )
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceIconWidth, heightDp = raceIconHeight)
@Composable
private fun PreviewIconAndText() {
    WidgetThemePreview {
        RaceIcon(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showText = true
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceIconWidth, heightDp = raceIconHeight)
@Composable
private fun PreviewIconOnly() {
    WidgetThemePreview {
        RaceIcon(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showText = false
        )
    }
}