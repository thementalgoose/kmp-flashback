package tmg.flashback.feature.season.presentation.calendar

import kotlinx.datetime.LocalDate
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.notifications.NotificationSchedule

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
    )

    data class GroupedCompletedRaces(
        val first: OverviewRace,
        val last: OverviewRace?
    ): CalendarItem(
        key = "collapsable-${first.round}-${last?.round}"
    )
}