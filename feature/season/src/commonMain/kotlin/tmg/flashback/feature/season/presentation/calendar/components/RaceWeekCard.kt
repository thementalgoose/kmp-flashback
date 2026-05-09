package tmg.flashback.feature.season.presentation.calendar.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.domain.formula1.generated.resources.ic_status_results_qualifying
import flashback.domain.formula1.generated.resources.ic_status_results_race
import flashback.domain.formula1.generated.resources.ic_status_results_sprint
import flashback.domain.formula1.generated.resources.weather_unknown
import flashback.feature.season.generated.resources.ic_notification_indicator_bell
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_has_qualifying_results
import flashback.presentation.localisation.generated.resources.ab_has_race_results
import flashback.presentation.localisation.generated.resources.ab_has_sprint_results
import flashback.presentation.localisation.generated.resources.ab_no_qualifying_results
import flashback.presentation.localisation.generated.resources.ab_no_race_results
import flashback.presentation.localisation.generated.resources.ab_no_sprint_results
import flashback.presentation.localisation.generated.resources.ab_notifications_enabled
import flashback.presentation.localisation.generated.resources.ab_schedule_date_card
import flashback.presentation.localisation.generated.resources.ab_schedule_date_card_notifications_enabled
import flashback.presentation.localisation.generated.resources.empty
import flashback.presentation.localisation.generated.resources.weekend_race_round
import flashback.feature.season.generated.resources.Res
import flashback.feature.season.generated.resources.ic_cancelled
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.feature.season.models.NotificationSchedule
import tmg.flashback.feature.season.presentation.calendar.CalendarItem
import tmg.flashback.formula1.constants.Formula1.qualifyingDataAvailableFrom
import tmg.flashback.formula1.constants.Formula1.sprintsIntroducedIn
import tmg.flashback.formula1.enums.SprintFormat
import tmg.flashback.formula1.enums.SprintFormat.Companion.getSeasonFormat
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.Timestamp
import tmg.flashback.formula1.model.Timestamp.TimestampState.BUILD_UP
import tmg.flashback.formula1.model.Timestamp.TimestampState.LIVE
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.datetime.displayDate
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek
import tmg.flashback.infrastructure.datetime.timeFormatHHmm
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextSection
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.flag.Flag
import tmg.flashback.ui.components.indicators.IndicatorDot
import tmg.flashback.ui.components.indicators.stateBorder
import tmg.flashback.ui.components.now.Now

private val countryBadgeSize = 32.dp
private const val pastScheduleAlpha = 0.2f
private val weatherIconSize = 42.dp

//region Schedule view

@Composable
internal fun RaceWeekCard(
    model: CalendarItem.RaceWeek,
    itemClicked: (CalendarItem.RaceWeek) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
        .background(when (model.shouldShowScheduleList) {
            true -> AppTheme.colors.surfaceContainer3
            false -> Color.Transparent
        })
        .alpha(if (model.model.cancelled) 0.6f else 1f)
        .clickable { itemClicked(model) }
    ) {
        Row {
            Box(modifier = Modifier
                .padding(
                    top = AppTheme.dimens.medium,
                    end = AppTheme.dimens.nsmall
                )
            ) {
                if (model.model.date.startOfWeek() == LocalDate.now().startOfWeek()) {
                    Now(Modifier.align(Alignment.CenterStart))
                }
                if (model.model.cancelled) {
                    Icon(
                        modifier = Modifier
                            .padding(start = AppTheme.dimens.medium)
                            .size(countryBadgeSize),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_cancelled),
                        tint = AppTheme.colors.onSurfaceVariant
                    )
                } else {
                    Flag(
                        iso = model.model.countryISO,
                        nationality = null,
                        modifier = Modifier
                            .padding(start = AppTheme.dimens.medium)
                            .size(countryBadgeSize),
                    )
                }
            }
            Column(modifier = Modifier
                .weight(1f)
                .padding(
                    top = AppTheme.dimens.small,
                    bottom = AppTheme.dimens.small,
                    end = AppTheme.dimens.medium
                )
            ) {
                Row {
                    TextTitle(
                        text = model.model.raceName,
                        bold = true,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(AppTheme.dimens.small))
                    Round(
                        round = model.model.round
                    )
                }
                Row {
                    TextBody1(
                        text = model.model.circuitName,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 2.dp)
                    )
                    Spacer(Modifier.width(AppTheme.dimens.small))
                    if (!model.model.cancelled) {
                        IconRow(
                            season = model.model.season,
                            hasQualifying = model.model.hasQualifying && model.model.season >= qualifyingDataAvailableFrom,
                            showSprint = (model.containsSprintEvent || model.model.hasSprint) && model.model.season > sprintsIntroducedIn,
                            hasSprint = model.model.hasSprint && model.model.season > sprintsIntroducedIn,
                            hasRace = model.model.hasResults
                        )
                    }
                }

                if (!model.shouldShowScheduleList) {
                    TextBody2(
                        text = model.model.date.displayDate(includeYear = false),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp)
                    )
                }
            }
        }
        if (model.shouldShowScheduleList) {
            Dates(
                scheduleList = model.model.schedule,
                notificationSchedule = model.notificationSchedule,
                modifier = Modifier.padding(top = AppTheme.dimens.xsmall)
            )
        }
    }
}

@Composable
private fun RowScope.IconRow(
    season: Int,
    hasQualifying: Boolean,
    showSprint: Boolean,
    hasSprint: Boolean,
    hasRace: Boolean,
) {
    val format = getSeasonFormat(season)
    if (showSprint && format == SprintFormat.FORMAT_CURRENT) {
        IconResult(
            icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_status_results_sprint,
            contentDescriptionAvailable = string.ab_has_sprint_results,
            contentDescriptionNotAvailable = string.ab_no_sprint_results,
            hasResult = hasSprint
        )
        Spacer(Modifier.width(2.dp))
    }
    IconResult(
        icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_status_results_qualifying,
        contentDescriptionAvailable = string.ab_has_qualifying_results,
        contentDescriptionNotAvailable = string.ab_no_qualifying_results,
        hasResult = hasQualifying
    )
    Spacer(Modifier.width(2.dp))
    if (showSprint && format == SprintFormat.FORMAT_2021_2022 || format == SprintFormat.FORMAT_2023) {
        IconResult(
            icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_status_results_sprint,
            contentDescriptionAvailable = string.ab_has_sprint_results,
            contentDescriptionNotAvailable = string.ab_no_sprint_results,
            hasResult = hasSprint
        )
        Spacer(Modifier.width(2.dp))
    }
    IconResult(
        icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_status_results_race,
        contentDescriptionAvailable = string.ab_has_race_results,
        contentDescriptionNotAvailable = string.ab_no_race_results,
        hasResult = hasRace
    )
}

@Composable
private fun RowScope.IconResult(
    icon: DrawableResource,
    contentDescriptionAvailable: StringResource,
    contentDescriptionNotAvailable: StringResource,
    hasResult: Boolean,
    iconSize: Dp = 16.dp
) {
    Icon(
        modifier = Modifier
            .size(iconSize)
            .align(Alignment.CenterVertically),
        painter = painterResource(resource = icon),
        contentDescription = when (hasResult) {
            true -> stringResource(resource = contentDescriptionAvailable)
            false -> stringResource(resource = contentDescriptionNotAvailable)
        },
        tint = when (hasResult) {
            true -> AppTheme.colors.f1ResultsFull
            false -> AppTheme.colors.f1ResultsNeutral.copy(alpha = 0.3f)
        }
    )
}

@Composable
private fun Dates(
    scheduleList: List<Schedule>,
    notificationSchedule: NotificationSchedule,
    modifier: Modifier = Modifier
) {
    val schedule = scheduleList.groupBy { it.timestamp.deviceLocalDateTime.date }
    val now = LocalDate.now()
    val targetIndex = schedule
        .map { it.key }
        .indexOfFirst { it == now }
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = (targetIndex.takeIf { it != -1 } ?: (schedule.size - 1)).coerceIn(0, schedule.size - 1)
    )

    val showWeather = schedule.values.map { it }.flatten().all { it.weather != null }

    LazyRow(
        modifier = modifier.padding(bottom = AppTheme.dimens.small),
        state = scrollState,
        content = {
            item {
                Spacer(Modifier.width(countryBadgeSize + (AppTheme.dimens.nsmall + AppTheme.dimens.medium)))
            }
            for ((date, list) in schedule) {
                val alpha = if (date < LocalDate.now() || list.all { it.timestamp.isInPast }) pastScheduleAlpha else 1f
                item {
                    Column(Modifier.semantics {
                        this.collectionInfo = CollectionInfo(list.size, 1)
                    }) {
                        TextBody2(
                            text = date.displayDate(weekdays = DayOfWeekNames.ENGLISH_FULL, includeYear = false),
                            modifier = Modifier
                                .alpha(alpha)
                                .padding(
                                    bottom = AppTheme.dimens.xsmall
                                )
                        )
                        Row {
                            list.forEach {
                                DateCard(
                                    schedule = it,
                                    showWeather = showWeather,
                                    showNotificationBadge = false
                                )
                                Spacer(Modifier.width(AppTheme.dimens.xsmall))
                            }
                        }
                    }
                    Spacer(Modifier.width(AppTheme.dimens.medium - AppTheme.dimens.xsmall))
                }
            }
        }
    )
}

@Composable
internal fun Round(
    round: Int
) {
    val contentDescription = stringResource(resource = string.weekend_race_round, round)
    TextSection(
        modifier = Modifier.semantics {
            this.contentDescription = contentDescription
        },
        text = "#${round}"
    )
}

@Composable
private fun DateCard(
    schedule: Schedule,
    showNotificationBadge: Boolean,
    showWeather: Boolean,
    modifier: Modifier = Modifier
) {
    val state = remember { schedule.timestamp.state }
    val color = when (state) {
        BUILD_UP -> AppTheme.colors.f1EventBuildup
        LIVE -> AppTheme.colors.f1EventLive
        else -> Color.Transparent
    }

    val time = schedule.timestamp.deviceLocalDateTime.time.format(timeFormatHHmm)
    val contentDescription = when (showNotificationBadge) {
        true -> stringResource(resource = string.ab_schedule_date_card_notifications_enabled, schedule.label, time)
        false -> stringResource(resource = string.ab_schedule_date_card, schedule.label, time)
    }
    Column(modifier = modifier
        .semantics(mergeDescendants = true) { }
        .clearAndSetSemantics { this.contentDescription = contentDescription }
        .width(IntrinsicSize.Max)
        .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
        .background(AppTheme.colors.surfaceContainer5)
        .stateBorder(color)
        .alpha(if (state == Timestamp.TimestampState.EXPIRED) pastScheduleAlpha else 1f)
        .padding(
            bottom = AppTheme.dimens.nsmall,
            start = AppTheme.dimens.nsmall,
            end = AppTheme.dimens.nsmall
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = AppTheme.dimens.nsmall,
                bottom = AppTheme.dimens.small
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state == BUILD_UP || state == LIVE) {
                IndicatorDot(
                    color = color,
                    size = 16.dp
                )
                Spacer(Modifier.width(AppTheme.dimens.xsmall))
            }
            TextBody1(
                textAlign = TextAlign.Center,
                text = schedule.label,
                textColor = AppTheme.colors.onSurfaceVariant
            )
            if (showNotificationBadge) {
                Spacer(Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(16.dp),
                    tint = AppTheme.colors.onSurface,
                    painter = painterResource(resource = Res.drawable.ic_notification_indicator_bell),
                    contentDescription = stringResource(resource = string.ab_notifications_enabled)
                )
            }
        }
        TextBody1(
            textColor = AppTheme.colors.onSurface,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = time,
            bold = true
        )
        if (showWeather && schedule.weather != null) {
            Spacer(Modifier.height(AppTheme.dimens.small))

            val summary = schedule.weather!!.summary.firstOrNull()
            Image(
                painter = painterResource(resource = summary?.icon ?: flashback.domain.formula1.generated.resources.Res.drawable.weather_unknown),
                contentDescription = stringResource(resource = summary?.label ?: string.empty),
                modifier = Modifier.size(weatherIconSize)
            )
        } else {
            Spacer(Modifier.height(AppTheme.dimens.nsmall))
        }
    }
}

@PreviewTheme
@Composable
private fun PreviewUpcoming() {
    ApplicationThemePreview {
        RaceWeekCard(
            model = CalendarItem.RaceWeek(
                model = OverviewRace.preview(),
                showScheduleList = true,
                notificationSchedule = NotificationSchedule(false, false, false, false, false, false),
            ),
            itemClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewUpcomingFuture() {
    ApplicationThemePreview {
        RaceWeekCard(
            model = CalendarItem.RaceWeek(
                model = OverviewRace.preview(),
                showScheduleList = false,
                notificationSchedule = NotificationSchedule(false, false, false, false, false, false),
            ),
            itemClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewPast() {
    ApplicationThemePreview {
        RaceWeekCard(
            model = CalendarItem.RaceWeek(
                model = OverviewRace.preview(
                    hasQualifying = true,
                    hasSprint = true,
                    hasResults = true
                ),
                showScheduleList = false,
                notificationSchedule = NotificationSchedule(false, false, false, false, false, false),
            ),
            itemClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewCancelled() {
    ApplicationThemePreview {
        RaceWeekCard(
            model = CalendarItem.RaceWeek(
                model = OverviewRace.preview(
                    cancelled = true
                ),
                showScheduleList = false,
                notificationSchedule = NotificationSchedule(false, false, false, false, false, false),
            ),
            itemClicked = { }
        )
    }
}