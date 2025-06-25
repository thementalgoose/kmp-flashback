package tmg.flashback.notifications.manager

actual class NotificationManagerImpl: NotificationManager {
    actual override fun isChannelEnabled(channelId: String): Boolean {
        return true
    }

    actual override fun createChannel(
        channelId: String,
        title: String,
        body: String
    ) {

    }

    actual override fun cancelChannel(channelId: String) {

    }
}