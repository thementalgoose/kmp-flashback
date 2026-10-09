package tmg.flashback.feature.notifications.model

data class NotificationResultsAvailableState(
    val race: Boolean,
    val sprint: Boolean,
    val sprintQualifying: Boolean,
    val qualifying: Boolean,
)
