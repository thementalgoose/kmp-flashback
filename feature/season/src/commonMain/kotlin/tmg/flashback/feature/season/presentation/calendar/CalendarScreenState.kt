package tmg.flashback.feature.season.presentation.calendar

import kotlinx.datetime.LocalDate
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.notifications.NotificationSchedule
import tmg.flashback.infrastructure.datetime.now

data class CalendarScreenState(
    val season: Int,
    val items: List<CalendarItem>? = listOf(),
    val isLoading: Boolean = false,
    val showEvents: Boolean = false
)

sealed class CalendarItem(
    val key: String
) {
    data class Event(
        val event: tmg.flashback.formula1.model.Event
    ): CalendarItem(
        key = "event-${event.label}-${event.date}"
    ) {
        val date: LocalDate
            get() = event.date
    }

    data class EmptyWeek(
        val monday: LocalDate,
    ): CalendarItem(
        key = "empty-$monday"
    )
    data class RaceWeek(
        val model: OverviewRace,
        private val showScheduleList: Boolean = false,
        val notificationSchedule: NotificationSchedule,
        val id: String = model.raceName
    ): CalendarItem(
        key = "${model.season}-${model.round}-${model.raceName}"
    ) {
        val date: LocalDate
            get() = model.date

        val containsSprintEvent: Boolean by lazy {
            model.schedule.any { it.label.contains("sprint", ignoreCase = true) }
        }

        val shouldShowScheduleList = showScheduleList && model.schedule.isNotEmpty()

        val fadeItem: Boolean
            get() = model.date > LocalDate.now() && !shouldShowScheduleList
    }

    data class GroupedCompletedRaces(
        val first: OverviewRace,
        val last: OverviewRace?
    ): CalendarItem(
        key = "collapsable-${first.round}-${last?.round}"
    )
}