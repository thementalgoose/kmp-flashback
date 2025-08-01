package tmg.flashback.presentation.settings.notifications.upcoming

import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationUpcoming

data class SettingsNotificationUpcomingUiState(
    val reminder: NotificationReminder,
    val reminderEnabled: Boolean,
    val enabled: Set<NotificationUpcoming>
)