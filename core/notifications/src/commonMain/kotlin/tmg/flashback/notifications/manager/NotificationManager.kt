package tmg.flashback.notifications.manager

import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Single

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
    fun cancelAll(
        uuids: List<Int>
    )

    val canScheduleExact: Boolean

    fun isChannelActive(channelId: String): Boolean
}

@Single(binds = [NotificationManager::class])
expect class NotificationManagerImpl(): NotificationManager {
    override fun schedule(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    )
    override fun cancel(uuid: Int)
    override val canScheduleExact: Boolean
    override fun isChannelActive(channelId: String): Boolean
}