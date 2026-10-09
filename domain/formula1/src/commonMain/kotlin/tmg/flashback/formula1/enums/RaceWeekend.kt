package tmg.flashback.formula1.enums

import kotlinx.serialization.Serializable
import tmg.flashback.formula1.model.Schedule

@Serializable
enum class RaceWeekend {
    FREE_PRACTICE,
    QUALIFYING,
    SPRINT,
    SPRINT_QUALIFYING,
    RACE
}

fun Schedule.toRaceWeekend(season: Int? = null): RaceWeekend? =
    label.toRaceWeekend(season)

fun String.toRaceWeekend(season: Int? = null): RaceWeekend? {
    val clean = this.lowercase()
    return when {
        clean.includes("shootout", "sprint shootout") -> RaceWeekend.SPRINT_QUALIFYING
        clean.includes("sprint qualifying", "sprint quali") -> {
            if (season != null && season in 2021..2022) {
                RaceWeekend.SPRINT
            } else {
                RaceWeekend.SPRINT_QUALIFYING
            }
        }
        clean.includes("sprint") -> RaceWeekend.SPRINT
        clean.includes("qualifying", "quali") -> RaceWeekend.QUALIFYING
        clean.includes("race", "grand prix") -> RaceWeekend.RACE
        clean.includes("fp", "free practice", "practice") -> RaceWeekend.FREE_PRACTICE
        else -> null
    }
}

private fun String.includes(vararg partials: String): Boolean {
    return partials.any { partial -> this.contains(partial) }
}