package tmg.flashback.widgets.upnext.presentation.layout

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import tmg.flashback.feature.widget_upnext.R
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.modifiers.surface
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody
import tmg.flashback.widgets.upnext.presentation.UpNextWidgetRefreshWidget
import tmg.flashback.widgets.upnext.presentation.components.CountryIcon
import tmg.flashback.widgets.upnext.presentation.components.FeatureDate
import tmg.flashback.widgets.upnext.presentation.components.Schedule
import tmg.flashback.widgets.upnext.presentation.components.TrackIcon
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.utils.raceSchedule

private const val scheduleRaceWidth = 320
private const val scheduleRaceHeight = 220

@Composable
internal fun ScheduleListRace(
    context: Context,
    overviewRace: OverviewRace,
    showTrackIcon: Boolean,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .surface(GlanceTheme.colors.background.getColor(context))
    ) {
        Row(GlanceModifier.fillMaxWidth()) {
            CountryIcon(
                context = context,
                country = overviewRace.country,
                countryISO = overviewRace.countryISO
            )
            TextBody(
                modifier = GlanceModifier
                    .defaultWeight()
                    .padding(
                        start = 8.dp,
                        end = 8.dp
                    ),
                text = overviewRace.raceName,
                color = GlanceTheme.colors.onBackground.getColor(context)
            )
            Image(
                modifier = GlanceModifier.clickable(actionRunCallback<UpNextWidgetRefreshWidget>()),
                provider = ImageProvider(R.drawable.ic_widget_refresh),
                contentDescription = null, // TODO: Localisation
            )
        }

        FeatureDate(
            overviewRace = overviewRace,
            context = context,
            modifier = GlanceModifier.defaultWeight()
        )

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(modifier = GlanceModifier.defaultWeight()) {
                overviewRace.schedule
                    .filter { it != overviewRace.raceSchedule() }
                    .forEach {
                        Schedule(
                            model = it,
                            compressed = false,
                            context = context
                        )
                    }
            }
            if (showTrackIcon) {
                TrackIcon(
                    context = context,
                    season = overviewRace.season,
                    raceName = overviewRace.raceName,
                    circuitId = overviewRace.circuitId,
                    circuitName = overviewRace.circuitName,
                    trackColour = GlanceTheme.colors.onBackground.getColor(context)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = scheduleRaceWidth, heightDp = scheduleRaceHeight)
@Composable
private fun PreviewTrack() {
    WidgetThemePreview {
        ScheduleListRace(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showTrackIcon = true
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = scheduleRaceWidth, heightDp = scheduleRaceHeight)
@Composable
private fun PreviewNoTrack() {
    WidgetThemePreview {
        ScheduleListRace(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showTrackIcon = false
        )
    }
}