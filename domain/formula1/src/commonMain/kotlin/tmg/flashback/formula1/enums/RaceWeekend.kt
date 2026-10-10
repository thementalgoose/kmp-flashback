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
