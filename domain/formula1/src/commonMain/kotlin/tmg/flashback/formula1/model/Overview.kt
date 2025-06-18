package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import tmg.flashback.infrastructure.datetime.now

data class Overview(
    val season: Int,
    val overviewRaces: List<OverviewRace>
) {
    val completed: Int
        get() = overviewRaces.count { it.date < LocalDate.now() }
    val upcoming: Int
        get() = overviewRaces.count { it.date >= LocalDate.now() }
    val scheduledToday: Int
        get() = overviewRaces.count { it.date == LocalDate.now() }

    companion object
}