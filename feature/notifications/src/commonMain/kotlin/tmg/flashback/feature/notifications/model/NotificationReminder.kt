package tmg.flashback.feature.notifications.model

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_reminder_mins_15
import flashback.presentation.localisation.generated.resources.notification_reminder_mins_30
import flashback.presentation.localisation.generated.resources.notification_reminder_mins_60
import org.jetbrains.compose.resources.StringResource

enum class NotificationReminder(
    val seconds: Int,
    val label: StringResource
) {
    MINUTES_60(3600, string.notification_reminder_mins_60),
    MINUTES_30(1800, string.notification_reminder_mins_30),
    MINUTES_15(900, string.notification_reminder_mins_15),
}