package tmg.flashback.widgets.upnext.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.widgets.upnext.presentation.components.WidgetTitle
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.preview.fakeSprintWeekend
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.marginSSmall
import tmg.flashback.widgets.upnext.presentation.style.marginSmall
import tmg.flashback.widgets.upnext.presentation.style.marginXSmall
import tmg.flashback.widgets.upnext.presentation.style.radius
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody1
import tmg.flashback.widgets.upnext.presentation.style.text.TextBody2
import tmg.flashback.widgets.upnext.utils.labels
import tmg.flashback.widgets.upnext.utils.weekRelativeLabel

internal const val raceOverviewWidth = 180
internal const val raceOverviewHeight = 130
internal val raceOverviewConfiguration = DpSize(raceOverviewWidth.dp, raceOverviewHeight.dp)

@Composable
internal fun RaceOverview(
    overviewRace: OverviewRace,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = GlanceModifier
        ) {
            item {
                WidgetTitle(
                    overviewRace = overviewRace,
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(
                            vertical = marginSmall,
                            horizontal = marginSmall,
                        )
                )
            }
            item {
                DayEvents(
                    overviewRace = overviewRace,
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(
                            bottom = marginSmall,
                            end = marginSmall,
                            start = marginSmall
                        )
                )
            }
        }
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
    ) {
        TextBody1(
            text = date.weekRelativeLabel()
        )
        schedules.forEach { schedule ->
            Box(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(vertical = marginXSmall)
            ) {
                Column(modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(GlanceTheme.colors.background)
                    .padding(
                        horizontal = marginSmall,
                        vertical = marginSmall
                    )
                    .cornerRadius(radius)
                ) {
                    val (_, time) = schedule.labels()
                    TextBody2(
                        maxLines = 2,
                        text = schedule.label,
                        weight = FontWeight.Bold
                    )
                    TextBody2(
                        text = time
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceOverviewWidth, heightDp = raceOverviewHeight)
@Composable
private fun PreviewOverview() {
    WidgetThemePreview {
        RaceOverview(
            overviewRace = fakeOverviewRace,
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = raceOverviewWidth, heightDp = raceOverviewHeight)
@Composable
private fun PreviewSprint() {
    WidgetThemePreview {
        RaceOverview(
            overviewRace = fakeSprintWeekend,
        )
    }
}