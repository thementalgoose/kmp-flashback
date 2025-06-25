package tmg.flashback.notifications.manager

interface NotificationManager {
    fun isChannelEnabled(channelId: String): Boolean
    fun createChannel(channelId: String, title: String, body: String)
    fun cancelChannel(channelId: String)
}

expect class NotificationManagerImpl(): NotificationManager {
    override fun isChannelEnabled(channelId: String): Boolean
    override fun createChannel(channelId: String, title: String, body: String)
    override fun cancelChannel(channelId: String)
}