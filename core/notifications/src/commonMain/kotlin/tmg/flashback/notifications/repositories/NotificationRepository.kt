package tmg.flashback.notifications.repositories

import tmg.flashback.preferences.manager.PreferenceManager

interface NotificationRepository {
    var remoteNotificationToken: String?
    var remoteNotificationTopics: Set<String>
    var notificationIds: Set<Int>
//    var notificationUuids: Set<String>
}

internal class NotificationRepositoryImpl(
    private val preferenceManager: PreferenceManager,
): NotificationRepository {

    companion object {
        private const val keyNotificationRemoteTopics: String = "NOTIFICATION_REMOTE_TOPICS"
        private const val keyNotificationIds: String = "NOTIFICATION_IDS"
//        private const val keyNotificationUuids: String = "NOTIFICATION_UUIDS"
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

//    override var notificationUuids: Set<String>
//        get() = preferenceManager.getSet(keyNotificationUuids, setOf())
//        set(value) = preferenceManager.save(keyNotificationUuids, value)
}