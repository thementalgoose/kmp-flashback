package tmg.flashback.feature.season.presentation.calendar

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format
import kotlinx.datetime.plus
import tmg.flashback.formula1.model.Event
import tmg.flashback.formula1.model.Overview
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.notifications.NotificationSchedule
import tmg.flashback.infrastructure.datetime.dateFormatYYYYMMDD
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek

internal object ScheduleBuilder {

    fun generateScheduleModel(
        overview: Overview,
        events: List<Event>,
        notificationSchedule: NotificationSchedule,
        showCollapsePreviousRaces: Boolean,
        showEmptyWeeks: Boolean,
    ): List<CalendarItem> {
        val list = mutableListOf<CalendarItem>()
        val next = overview.overviewRaces.getLatestUpcoming()
        val now = LocalDate.now()

        if (events.any() && overview.completed == 0 && events.any { it.date >= now }) {
            list.addAll(events.map {
                CalendarItem.Event(it)
            })
        }

        if (next == null || overview.upcoming == 0) {
            list.addAll(overview.overviewRaces.map {
                CalendarItem.RaceWeek(
                    model = it,
                    showScheduleList = false,
                    notificationSchedule = notificationSchedule
                )
            })
            return list
        }
        if (showCollapsePreviousRaces && next.round >= 4) {
            val twoRacesAgo = overview.overviewRaces.first { it.round == next.round - 2 }
            list.add(
                CalendarItem.GroupedCompletedRaces(
                    first = overview.overviewRaces.minBy { it.round },
                    last = twoRacesAgo
                )
            )
            val oneRaceAgo = overview.overviewRaces.first { it.round == next.round - 1 }
            if (showEmptyWeeks) {
                list.addAll(twoRacesAgo.getWeeksBetween(oneRaceAgo).map { CalendarItem.EmptyWeek(it) })
            }
            list.add(
                CalendarItem.RaceWeek(
                    model = oneRaceAgo,
                    showScheduleList = false,
                    notificationSchedule = notificationSchedule
                )
            )
            if (showEmptyWeeks) {
                list.addAll(oneRaceAgo.getWeeksBetween(next).map { CalendarItem.EmptyWeek(it) })
            }
        }

        overview.overviewRaces
            .filter {
                if (!(showCollapsePreviousRaces && next.round >= 4)) {
                    return@filter true
                }
                return@filter it.round >= next.round
            }
            .sortedBy { it.round }
            .forEachIndexed { index, overviewRace ->
                list.add(
                    CalendarItem.RaceWeek(
                        model = overviewRace,
                        showScheduleList = overviewRace.round == next.round,
                        notificationSchedule = notificationSchedule
                    )
                )
                val nextRace = overview.overviewRaces.firstOrNull { it.round == overviewRace.round + 1 }
                if (showEmptyWeeks && nextRace != null) {
                    list.addAll(overviewRace.getWeeksBetween(nextRace).map {
                        CalendarItem.EmptyWeek(
                            it
                        )
                    })
                }
            }

        return list
            .sortedBy {
                when (it) {
                    is CalendarItem.EmptyWeek -> it.monday.format(dateFormatYYYYMMDD)
                    is CalendarItem.GroupedCompletedRaces -> it.first.date.format(dateFormatYYYYMMDD)
                    is CalendarItem.Event -> it.date.format(dateFormatYYYYMMDD)
                    is CalendarItem.RaceWeek -> it.model.date.format(dateFormatYYYYMMDD)
                }
            }
    }

    private fun OverviewRace.getWeeksBetween(targetRace: OverviewRace): List<LocalDate> {
        val daysBetween = this.date.daysUntil(targetRace.date)
        val weeksToExclude = listOf(this.date.startOfWeek(), targetRace.date.startOfWeek())

        return List(daysBetween) { this.date.plus(it, DateTimeUnit.DAY) }
            .map { it.startOfWeek() }
            .distinct()
            .filter { !weeksToExclude.contains(it) }
    }

    private fun List<OverviewRace>.getLatestUpcoming(): OverviewRace? {
        return this
            .sortedBy { it.date }
            .firstOrNull { it.date >= LocalDate.now() }
    }
}