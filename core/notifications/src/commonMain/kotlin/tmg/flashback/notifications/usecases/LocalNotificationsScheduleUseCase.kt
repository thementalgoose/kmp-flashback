package tmg.flashback.notifications.usecases

import kotlinx.datetime.LocalDateTime
import tmg.flashback.notifications.manager.NotificationManager

interface LocalNotificationsScheduleUseCase {
    operator fun invoke(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime,
    )
}

internal class LocalNotificationsScheduleUseCaseImpl(
    private val notificationManager: NotificationManager
): LocalNotificationsScheduleUseCase {
    override operator fun invoke(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    ) {
        notificationManager.schedule(
            uuid = uuid,
            channelId = channelId,
            title = title,
            text = text,
            timestamp = timestamp,
        )
    }
}