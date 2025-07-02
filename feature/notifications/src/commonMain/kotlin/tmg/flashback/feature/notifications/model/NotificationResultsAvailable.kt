package tmg.flashback.feature.notifications.model

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_channel_qualifying_notify
import flashback.presentation.localisation.generated.resources.notification_channel_race_notify
import flashback.presentation.localisation.generated.resources.notification_channel_sprint_notify
import flashback.presentation.localisation.generated.resources.notification_channel_sprint_qualifying_notify
import org.jetbrains.compose.resources.StringResource

enum class NotificationResultsAvailable(
    val channelId: String,
    val channelLabel: StringResource,
    val remoteSubscriptionTopic: String = channelId
) {
    RACE("notify_race", string.notification_channel_race_notify),
    SPRINT("notify_sprint", string.notification_channel_sprint_notify),
    SPRINT_QUALIFYING("notify_sprint_qualifying", string.notification_channel_sprint_qualifying_notify),
    QUALIFYING("notify_qualifying", string.notification_channel_qualifying_notify)
}