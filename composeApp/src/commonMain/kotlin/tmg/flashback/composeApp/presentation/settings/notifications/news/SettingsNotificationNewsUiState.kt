package tmg.flashback.composeApp.presentation.settings.notifications.news

import tmg.flashback.feature.notifications.model.NotificationNews

data class SettingsNotificationNewsUiState(
    val enabled: Set<NotificationNews>
)