package tmg.flashback.feature.notifications.repositories

import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_30
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_60
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.preferences.manager.PreferenceManager
import org.koin.core.annotation.Single
import tmg.flashback.feature.notifications.model.NotificationNews

interface NotificationSettingsRepository {
    var notificationReminderPeriod: NotificationReminder
    var notificationUpcomingEnabled: Set<NotificationUpcoming>
    var notificationResultsEnabled: Set<NotificationResultsAvailable>
    var notificationNewsEnabled: Set<NotificationNews>
    var notificationPromptSeen: Boolean
}

@Single
internal class NotificationSettingsRepositoryImpl(
    private val preferenceManager: PreferenceManager
): NotificationSettingsRepository {

    override var notificationReminderPeriod: NotificationReminder
        get() = preferenceManager
            .getInt(keyNotificationReminder, MINUTES_60.seconds)
            .toEnum<NotificationReminder> { it.seconds } ?: MINUTES_60
        set(value) = preferenceManager.save(keyNotificationReminder, value.seconds)

    override var notificationResultsEnabled: Set<NotificationResultsAvailable>
        get() = preferenceManager
            .getSet(keyNotificationResult, NotificationResultsAvailable.entries.map { it.saveKey }.toSet())
            .mapNotNull { key -> key.toEnum<NotificationResultsAvailable> { it.saveKey } }
            .distinct()
            .toSet()
        set(value) = preferenceManager.save(keyNotificationResult, value.map { it.saveKey }.toSet())

    override var notificationPromptSeen: Boolean
        get() = preferenceManager.getBoolean(keyPromptNotifications, false)
        set(value) = preferenceManager.save(keyPromptNotifications, value)

    override var notificationUpcomingEnabled: Set<NotificationUpcoming>
        get() = preferenceManager
            .getSet(keyNotificationUpcoming, NotificationUpcoming.entries.map { it.saveKey }.toSet())
            .mapNotNull { key -> key.toEnum<NotificationUpcoming> { it.saveKey } }
            .distinct()
            .toSet()
        set(value) = preferenceManager.save(keyNotificationUpcoming, value.map { it.saveKey }.toSet())

    override var notificationNewsEnabled: Set<NotificationNews>
        get() = preferenceManager
            .getSet(keyNotificationNews, NotificationNews.entries.map { it.saveKey }.toSet())
            .mapNotNull { key -> key.toEnum<NotificationNews> { it.saveKey } }
            .distinct()
            .toSet()
        set(value) = preferenceManager.save(keyNotificationNews, value.map { it.saveKey }.toSet())

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

        internal val NotificationNews.saveKey: String
            get() = when (this) {
                NotificationNews.BREAKING_NEWS -> "breaking_news"
            }


        private const val keyPromptNotifications: String = "RUNTIME_NOTIFICATION_PROMPT"
        private const val keyNotificationUpcoming: String = "NOTIFICATION_UPCOMING_CATEGORIES"
        private const val keyNotificationResult: String = "NOTIFICATION_RESULT_AVAILABLE_CATEGORIES"

        private const val keyNotificationNews: String = "NOTIFICATION_NEWS_AVAILABLE_CATEGORIES"

        private const val keyNotificationReminder: String = "UP_NEXT_NOTIFICATION_REMINDER"
    }
}