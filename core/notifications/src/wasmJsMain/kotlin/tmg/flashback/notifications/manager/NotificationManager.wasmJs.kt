package tmg.flashback.notifications.manager

import kotlinx.datetime.LocalDateTime

actual class NotificationManagerImpl actual constructor() : NotificationManager {
    actual override fun schedule(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    ) { }

    actual override fun cancel(uuid: Int) { }

    override fun cancelAll(uuids: List<Int>) { }

    actual override val canScheduleExact: Boolean get() = false
}
