package tmg.flashback.feature.notifications.model

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class NotificationUpcoming(
    val channelId: String,
    val channelLabel: StringResource,
) {
    RACE("flashback_race", string.notification_channel_free_practice),
    SPRINT("flashback_sprint", string.notification_channel_qualifying),
    SPRINT_QUALIFYING("flashback_sprint_qualifying", string.notification_channel_sprint_qualifying),
    QUALIFYING("flashback_qualifying", string.notification_channel_sprint),
    FREE_PRACTICE("flashback_free_practice", string.notification_channel_race),
    OTHER("flashback_info", string.notification_channel_info)
}