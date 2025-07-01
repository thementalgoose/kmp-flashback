package tmg.flashback.notifications.usecases

import tmg.flashback.notifications.repositories.NotificationRepository

interface LocalNotificationsScheduleUseCase {
    fun schedule(
        channelId: String,
        id: Int,
        title: String,
        body: String,
        payloadData: Map<String, String>
    )
}

internal class LocalNotificationsScheduleUseCaseImpl(
    private val notificationRepository: NotificationRepository
): LocalNotificationsScheduleUseCase {
    override fun schedule(
        channelId: String,
        id: Int,
        title: String,
        body: String,
        payloadData: Map<String, String>
    ) {
        NotifierManager.getLocalNotifier().notify(
            id = id,
            title = title,
            body = body,
            payloadData = payloadData,
        )
        notificationRepository.notificationIds += id
    }
}