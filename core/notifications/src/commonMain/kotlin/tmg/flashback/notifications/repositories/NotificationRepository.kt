package tmg.flashback.notifications.repositories

import tmg.flashback.notifications.manager.NotificationManager
import tmg.flashback.preferences.manager.PreferenceManager

interface NotificationRepository {
    var remoteNotificationToken: String?
    var remoteNotificationTopics: Set<String>
    var notificationIds: Set<Int>
}

internal class NotificationRepositoryImpl(
    private val preferenceManager: PreferenceManager,
    private val notificationManager: NotificationManager
): NotificationRepository {

    companion object {
        private const val keyNotificationRemoteTopics: String = "NOTIFICATION_REMOTE_TOPICS"
        private const val keyNotificationIds: String = "NOTIFICATION_IDS"
        private const val keyNotificationRemoteToken: String = "NOTIFICATION_REMOTE_TOKEN"
    }

    override var remoteNotificationToken: String?
        get() = preferenceManager.getString(keyNotificationRemoteToken, null)
        set(value) = preferenceManager.save(keyNotificationRemoteToken, value ?: "")

    override var remoteNotificationTopics: Set<String>
        get() = preferenceManager.getSet(keyNotificationRemoteTopics, emptySet())
        set(value) = preferenceManager.save(keyNotificationRemoteTopics, value)

    override var notificationIds: Set<Int>
        get() = preferenceManager.getSet(keyNotificationIds, setOf())
            .mapNotNull { it.toIntOrNull() }
            .toSet()
        set(value) = preferenceManager.save(keyNotificationIds, value.map { it.toString() }.toSet())

    fun isChannelEnabled(channelId: String): Boolean {
        return notificationManager.isChannelEnabled(channelId)
    }
}