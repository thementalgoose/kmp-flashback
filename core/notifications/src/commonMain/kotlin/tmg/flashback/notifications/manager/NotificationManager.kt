package tmg.flashback.notifications.manager

import kotlinx.datetime.LocalDateTime

interface NotificationManager {
    fun schedule(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    )

    fun cancel(
        uuid: Int
    )
}

expect class NotificationManagerImpl(): NotificationManager {
    override fun schedule(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    )
    override fun cancel(uuid: Int)
}