package tmg.flashback.presentation.settings.notifications.results

import tmg.flashback.feature.notifications.model.NotificationResultsAvailable

data class SettingsNotificationResultsUiState(
    val enabled: Set<NotificationResultsAvailable>
)