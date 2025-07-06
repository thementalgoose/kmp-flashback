package tmg.flashback.notifications.manager

import kotlinx.datetime.LocalDateTime

actual class NotificationManagerImpl actual constructor(): NotificationManager {
    actual override fun schedule(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    ) {
        TODO("Not yet implemented")
    }

    actual override fun cancel(uuid: Int) {
        TODO("Not yet implemented")
    }
}