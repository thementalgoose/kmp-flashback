package tmg.flashback.widgets.upnext.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import kotlinx.datetime.LocalDate
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.widgets.upnext.presentation.components.WidgetTitle
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.preview.fakeSprintWeekend
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.marginSmall
import tmg.flashback.widgets.upnext.presentation.style.marginXSmall
import tmg.flashback.widgets.upnext.presentation.style.radius
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody2
import tmg.flashback.widgets.upnext.utils.labels
import tmg.flashback.widgets.upnext.utils.weekRelativeLabel

internal const val raceOverviewWidth = 250
internal const val raceOverviewHeight = 190
private const val raceOverviewHeightBreakpoint = 205

@get:Composable
private val ScheduleBackground
    get() = GlanceTheme.colors.primaryContainer

@get:Composable
private val ScheduleText
    get() = GlanceTheme.colors.onPrimaryContainer

@Composable
internal fun RaceOverview(
    overviewRace: OverviewRace,
    localSize: DpSize,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        WidgetTitle(
            overviewRace = overviewRace,
            showCircuit = localSize.height >= raceOverviewHeightBreakpoint.dp,
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(
                    vertical = marginSmall,
                    horizontal = marginSmall,
                )
        )
        DayEvents(
            overviewRace = overviewRace,
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(
                    bottom = marginXSmall,
                    end = marginSmall,
                    start = marginSmall
                )
        )
    }
}

@Composable
private fun DayEvents(
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier
) {
    val schedules = remember {
        overviewRace
            .schedule
            .sortedBy { it.time }
            .sortedBy { it.date }
            .groupBy { it.timestamp.deviceLocalDateTime.date }
            .toList()
    }
    Row(
        modifier = modifier
    ) {
        schedules.forEachIndexed { index, pair ->
            val (date, scheduleList) = pair
            if (index != 0) {
                Spacer(modifier = GlanceModifier.width(marginSmall))
            }
            Day(
                date = date,
                schedules = scheduleList,
                modifier = GlanceModifier.defaultWeight()
            )
        }
    }
}

@Composable
private fun Day(
    date: LocalDate,
    schedules: List<Schedule>,
    modifier: GlanceModifier = GlanceModifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        TextBody2(
            text = date.weekRelativeLabel()
        )
        schedules.forEach { schedule ->
            Box(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .defaultWeight()
                    .padding(vertical = marginXSmall)
            ) {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(ScheduleBackground)
                        .cornerRadius(radius),
                ) {
                    Column(
                        modifier = GlanceModifier.padding(
                            top = marginXSmall,
                            start = marginSmall,
                            end = marginSmall
                        ),
                    ) {
                        val (_, time) = schedule.labels()
                        TextBody2(
                            color = ScheduleText,
                            maxLines = 1,
                            text = schedule.label,
                            weight = FontWeight.Bold
                        )
                        TextBody2(
                            color = ScheduleText,
                            text = time
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceOverviewWidth, heightDp = raceOverviewHeight)
@Composable
private fun PreviewOverview1() {
    WidgetThemePreview {
        RaceOverview(
            overviewRace = fakeOverviewRace,
            localSize = LocalSize.current
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceOverviewWidth, heightDp = raceOverviewHeight)
@Composable
private fun PreviewSprint1() {
    WidgetThemePreview {
        RaceOverview(
            overviewRace = fakeSprintWeekend,
            localSize = LocalSize.current
        )
    }
}