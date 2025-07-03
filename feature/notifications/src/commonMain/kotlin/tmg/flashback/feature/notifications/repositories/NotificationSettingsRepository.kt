package tmg.flashback.feature.notifications.repositories

import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_30
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.preferences.manager.PreferenceManager

interface NotificationSettingsRepository {
    var notificationReminderPeriod: NotificationReminder

    var notificationUpcomingEnabled: Set<NotificationUpcoming>
    var notificationResultsEnabled: Set<NotificationResultsAvailable>
}

internal class NotificationSettingsRepositoryImpl(
    private val preferenceManager: PreferenceManager
): NotificationSettingsRepository {

    override var notificationReminderPeriod: NotificationReminder
        get() = preferenceManager
            .getInt(keyNotificationReminder, MINUTES_30.seconds)
            .toEnum<NotificationReminder> { it.seconds } ?: MINUTES_30
        set(value) = preferenceManager.save(keyNotificationReminder, value.seconds)

    override var notificationResultsEnabled: Set<NotificationResultsAvailable>
        get() = preferenceManager
            .getSet(keyNotificationResult, NotificationResultsAvailable.entries.map { it.saveKey }.toSet())
            .mapNotNull { key -> key.toEnum<NotificationResultsAvailable> { it.saveKey } }
            .distinct()
            .toSet()
        set(value) = preferenceManager.save(keyNotificationResult, value.map { it.saveKey }.toSet())

    override var notificationUpcomingEnabled: Set<NotificationUpcoming>
        get() = preferenceManager
            .getSet(keyNotificationUpcoming, NotificationUpcoming.entries.map { it.saveKey }.toSet())
            .mapNotNull { key -> key.toEnum<NotificationUpcoming> { it.saveKey } }
            .distinct()
            .toSet()
        set(value) = preferenceManager.save(keyNotificationUpcoming, value.map { it.saveKey }.toSet())

    companion object {

        internal val NotificationUpcoming.saveKey: String
            get() = when (this) {
                NotificationUpcoming.RACE -> "race"
                NotificationUpcoming.SPRINT -> "sprint_race"
                NotificationUpcoming.SPRINT_QUALIFYING -> "sprint_qualifying"
                NotificationUpcoming.QUALIFYING -> "qualifying"
                NotificationUpcoming.FREE_PRACTICE -> "free_practice"
                NotificationUpcoming.OTHER -> "other"
            }

        internal val NotificationResultsAvailable.saveKey: String
            get() = when (this) {
                NotificationResultsAvailable.RACE -> "race"
                NotificationResultsAvailable.SPRINT -> "sprint_race"
                NotificationResultsAvailable.SPRINT_QUALIFYING -> "sprint_qualifying"
                NotificationResultsAvailable.QUALIFYING -> "qualifying"
            }

        private const val keyNotificationUpcoming: String = "NOTIFICATION_UPCOMING_CATEGORIES"
        private const val keyNotificationResult: String = "NOTIFICATION_RESULT_AVAILABLE_CATEGORIES"

        private const val keyNotificationReminder: String = "UP_NEXT_NOTIFICATION_REMINDER"
    }
}