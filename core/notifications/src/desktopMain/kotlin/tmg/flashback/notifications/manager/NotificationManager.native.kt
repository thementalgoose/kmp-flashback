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
        /* TODO */
    }

    actual override fun cancel(uuid: Int) {
        /* TODO */
    }
}