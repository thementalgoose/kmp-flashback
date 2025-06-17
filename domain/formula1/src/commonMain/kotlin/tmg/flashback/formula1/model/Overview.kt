package tmg.flashback.formula1.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Overview(
    val season: Int,
    val overviewRaces: List<OverviewRace>
) {
    val completed: Int
        get() = overviewRaces.count { it.date < now }
    val upcoming: Int
        get() = overviewRaces.count { it.date >= now }
    val scheduledToday: Int
        get() = overviewRaces.count { it.date == now }

    companion object
}

private val now: LocalDate
    get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date