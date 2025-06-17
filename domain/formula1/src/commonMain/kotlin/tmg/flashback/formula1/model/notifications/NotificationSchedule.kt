package tmg.flashback.formula1.model.notifications

import tmg.flashback.formula1.enums.RaceWeekend
import tmg.flashback.formula1.utils.NotificationUtils

data class NotificationSchedule(
    val freePractice: Boolean,
    val qualifying: Boolean,
    val sprint: Boolean,
    val sprintQualifying: Boolean,
    val race: Boolean,
    val other: Boolean
) {
    fun getByLabel(label: String): Boolean {
        return when (NotificationUtils.getCategoryBasedOnLabel(label)) {
            RaceWeekend.FREE_PRACTICE -> freePractice
            RaceWeekend.QUALIFYING -> qualifying
            RaceWeekend.SPRINT_QUALIFYING -> sprintQualifying
            RaceWeekend.SPRINT -> sprint
            RaceWeekend.RACE -> race
            null -> other
        }
    }
}