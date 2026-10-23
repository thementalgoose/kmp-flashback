package tmg.flashback.feature.notifications.model

data class NotificationUpcomingState(
    val race: Boolean,
    val sprint: Boolean,
    val sprintQualifying: Boolean,
    val qualifying: Boolean,
    val freePractice: Boolean,
    val other: Boolean = false,
)
