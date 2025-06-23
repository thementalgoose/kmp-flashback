package tmg.flashback.feature.season.repositories

import tmg.flashback.preferences.manager.PreferenceManager

interface CalendarRepository {
    var viewedSeasons: Set<Int>
    var emptyWeeksInCalendar: Boolean
    var collapseList: Boolean
    var keepUserSelectedSeason: Boolean
    var userSelectedSeason: Int?
}

internal class CalendarRepositoryImpl(
    private val preferenceManager: PreferenceManager,
): CalendarRepository {

    companion object {

        // Prefs
        private const val keyEmptyWeeksInSchedule: String = "empty_weeks_in_schedule"
        private const val keySeenSeasons: String = "SEASONS_VIEWED"
        private const val keyDashboardCollapseList: String = "DASHBOARD_COLLAPSE_LIST"
        private const val keyRememberSeasonChange: String = "REMEMBER_SEASON_CHANGE"
        private const val keyUserSeasonChange: String = "USER_SEASON_CHANGE"
    }

    override var viewedSeasons: Set<Int>
        get() = preferenceManager.getSet(keySeenSeasons, emptySet())
            .mapNotNull { it.toIntOrNull() }
            .toSet()
        set(value) = preferenceManager.save(keySeenSeasons, value.map { it.toString() }.toSet())

    override var emptyWeeksInCalendar: Boolean
        get() = preferenceManager.getBoolean(keyEmptyWeeksInSchedule, false)
        set(value) = preferenceManager.save(keyEmptyWeeksInSchedule, value)

    override var collapseList: Boolean
        get() = preferenceManager.getBoolean(keyDashboardCollapseList, true)
        set(value) = preferenceManager.save(keyDashboardCollapseList, value)

    override var keepUserSelectedSeason: Boolean
        get() = preferenceManager.getBoolean(keyRememberSeasonChange, false)
        set(value) = preferenceManager.save(keyRememberSeasonChange, value)

    override var userSelectedSeason: Int?
        get() = preferenceManager.getInt(keyUserSeasonChange, -1).takeIf { it != -1 }
        set(value) = preferenceManager.save(keyUserSeasonChange, value ?: -1)
}