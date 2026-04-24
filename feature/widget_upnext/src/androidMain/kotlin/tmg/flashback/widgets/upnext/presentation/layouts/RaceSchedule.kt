package tmg.flashback.widgets.upnext.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import androidx.glance.text.TextAlign
import kotlinx.datetime.format
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.widgets.upnext.presentation.components.WidgetTitle
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.preview.fakeSprintWeekend
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.marginSmall
import tmg.flashback.widgets.upnext.presentation.style.marginXSmall
import tmg.flashback.widgets.upnext.presentation.style.marginXXSmall
import tmg.flashback.widgets.upnext.presentation.style.radius
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody1
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody2
import tmg.flashback.widgets.upnext.utils.labels
import tmg.flashback.widgets.upnext.utils.weekRelativeLabel


internal const val raceScheduleWidth = 140
internal const val raceScheduleHeight = 180

@get:Composable
private val ScheduleBackground
    get() = GlanceTheme.colors.primaryContainer

@get:Composable
private val ScheduleText
    get() = GlanceTheme.colors.onPrimaryContainer

@Composable
internal fun RaceSchedule(
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier,
) {
    val schedules = remember {
        overviewRace
            .schedule
            .sortedBy { it.time }
            .sortedBy { it.date }
            .groupBy { it.timestamp.deviceLocalDateTime.date }
            .toList()
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = GlanceModifier,
        ) {
            item {
                WidgetTitle(
                    overviewRace = overviewRace,
                    showCircuit = false,
                    titleMaxLines = 1,
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(
                            top = marginSmall,
                            start = marginSmall,
                            end = marginSmall
                        )
                )
            }
            for ((date, scheduleList) in schedules) {
                item {
                    TextBody2(
                        text = date.weekRelativeLabel(),
                        modifier = GlanceModifier
                            .padding(
                                start = marginSmall,
                                end = marginSmall,
                                bottom = marginSmall,
                                top = marginSmall
                            )
                    )
                }
                for (i in 0 until scheduleList.size) {
                    val schedule = scheduleList[i]
                    item {
                        Column(
                            modifier = GlanceModifier.padding(horizontal = marginSmall)
                        ) {
                            if (i != 0) {
                                Spacer(modifier = GlanceModifier.height(1.dp))
                            }
                            Schedule(
                                schedule = schedule,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Schedule(
    schedule: Schedule,
    modifier: GlanceModifier = GlanceModifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ScheduleBackground)
            .cornerRadius(radius)
            .padding(
                horizontal = marginSmall,
                vertical = marginXSmall
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val (label, time) = schedule.labels()
        if (label.lowercase() == "race") {
            TextBody1(
                color = ScheduleText,
                text = schedule.label,
                weight = FontWeight.Bold,
                maxLines = 2,
                modifier = GlanceModifier.defaultWeight()
                    .padding(end = marginXSmall)
            )
        } else {
            TextBody2(
                color = ScheduleText,
                text = schedule.label,
                weight = FontWeight.Bold,
                maxLines = 2,
                modifier = GlanceModifier.defaultWeight()
                    .padding(end = marginXSmall)
            )
        }
        TextBody2(
            color = ScheduleText,
            text = time,
            textAlign = TextAlign.End,
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceScheduleWidth, heightDp = raceScheduleHeight)
@Composable
private fun PreviewOverview() {
    WidgetThemePreview {
        RaceSchedule(
            overviewRace = fakeOverviewRace,
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceScheduleWidth, heightDp = raceScheduleHeight)
@Composable
private fun PreviewSprint() {
    WidgetThemePreview {
        RaceSchedule(
            overviewRace = fakeSprintWeekend,
        )
    }
}