package tmg.flashback.feature.season.models

// TODO: Populate this properly
data class NotificationSchedule(
    val freePractice: Boolean,
    val qualifying: Boolean,
    val sprint: Boolean,
    val sprintQualifying: Boolean,
    val race: Boolean,
    val other: Boolean
)