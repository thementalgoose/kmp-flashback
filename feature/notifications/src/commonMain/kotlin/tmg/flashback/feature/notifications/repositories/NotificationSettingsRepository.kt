package tmg.flashback.feature.notifications.repositories

import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_30
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.preferences.manager.PreferenceManager

interface NotificationSettingsRepository {
    var notificationReminderPeriod: NotificationReminder
}

internal class NotificationSettingsRepositoryImpl(
    private val preferenceManager: PreferenceManager
): NotificationSettingsRepository {

    override var notificationReminderPeriod: NotificationReminder
        get() = preferenceManager
            .getInt(keyNotificationReminder, MINUTES_30.seconds)
            .toEnum<NotificationReminder> { it.seconds } ?: MINUTES_30
        set(value) = preferenceManager.save(keyNotificationReminder, value.seconds)

    companion object {
        private const val keyNotificationReminder: String = "UP_NEXT_NOTIFICATION_REMINDER"
    }
}