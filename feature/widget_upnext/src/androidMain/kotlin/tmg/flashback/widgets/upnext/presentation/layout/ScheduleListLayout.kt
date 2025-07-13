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
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import tmg.flashback.feature.widget_upnext.R
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.modifiers.surface
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody
import tmg.flashback.widgets.upnext.presentation.UpNextWidgetRefreshWidget
import tmg.flashback.widgets.upnext.presentation.components.CountryIcon
import tmg.flashback.widgets.upnext.presentation.components.Schedule
import tmg.flashback.widgets.upnext.presentation.components.TrackIcon
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.utils.labels

private const val scheduleWidth = 280
private const val scheduleHeight = 180

@Composable
internal fun ScheduleList(
    context: Context,
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier,
    showTrackIcon: Boolean
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
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
                contentDescription = null,
            )
        }

        if (overviewRace.schedule.isEmpty()) {
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    val (day, time) = overviewRace.labels()
                    Row(modifier = modifier.padding(top = 12.dp)) {
                        TextBody(
                            modifier = GlanceModifier,
                            color = GlanceTheme.colors.onBackground.getColor(context),
                            text = "Race",
                            weight = FontWeight.Bold
                        )
                        Spacer(GlanceModifier.width(12.dp))
                        TextBody(
                            color = GlanceTheme.colors.onBackground.getColor(context),
                            text = "$day (${time})"
                        )
                    }
                }
            }
        } else {
            Row(modifier = GlanceModifier.defaultWeight()) { }
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                LazyColumn(modifier = GlanceModifier.defaultWeight()) {
                    val schedule = overviewRace.schedule
                    items(items = schedule) {
                        Schedule(
                            model = it,
                            context = context,
                            compressed = true
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
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = scheduleWidth, heightDp = scheduleHeight)
@Composable
private fun PreviewTrack() {
    WidgetThemePreview {
        ScheduleList(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showTrackIcon = true
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = scheduleWidth, heightDp = scheduleHeight)
@Composable
private fun PreviewNoTrack() {
    WidgetThemePreview {
        ScheduleList(
            context = LocalContext.current,
            overviewRace = fakeOverviewRace,
            showTrackIcon = false
        )
    }
}