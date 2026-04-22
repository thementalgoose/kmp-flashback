package tmg.flashback.widgets.upnext.presentation.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.text.FontWeight
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DateTimeFormatBuilder
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.widgets.upnext.presentation.components.CountryIcon
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.preview.Preview4x2
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody1
import tmg.flashback.widgets.upnext.presentation.style.text.TextTitle
import tmg.flashback.widgets.upnext.utils.labels
import java.time.format.DateTimeFormatter

@Composable
internal fun RaceDetailLayout(
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier,
) {
    val schedules = overviewRace.schedule
        .sortedBy { it.time }
        .sortedBy { it.date }
        .groupBy { it.timestamp.deviceLocalDateTime.date }
        .toList()
    Column(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Title(
            overviewRace
        )
        Row(modifier = GlanceModifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()) {
            schedules.forEach { (date, scheduleList) ->
                ScheduleList(
                    modifier = GlanceModifier
                        .padding(horizontal = 8.dp)
                        .defaultWeight(),
                    date = date,
                    schedules = scheduleList
                )
            }
        }
    }
}

@Composable
private fun Title(
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier,
) {
    val context = LocalContext.current
    Row(modifier = modifier) {
        CountryIcon(
            context = context,
            country = overviewRace.country,
            countryISO = overviewRace.countryISO
        )
        TextTitle(overviewRace.raceName)
    }
}

@Composable
private fun ScheduleList(
    date: LocalDate,
    schedules: List<Schedule>,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier
    ) {
        TextBody1("")
        schedules.forEach {
            Spacer(GlanceModifier.height(8.dp))
            ScheduleBlock(
                modifier = GlanceModifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .cornerRadius(8.dp),
                schedule = it
            )
        }
    }
}

@Composable
private fun ScheduleBlock(
    schedule: Schedule,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(GlanceTheme.colors.background)
    ) {
        val (_, time) = schedule.labels()
        TextBody1(schedule.label)
        TextBody1(time, weight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview4x2
@Composable
private fun PreviewTrack() {
    WidgetThemePreview {
        RaceDetailLayout(
            overviewRace = fakeOverviewRace,
        )
    }
}