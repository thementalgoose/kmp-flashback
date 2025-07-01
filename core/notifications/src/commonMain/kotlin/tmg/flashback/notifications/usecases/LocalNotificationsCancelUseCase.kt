package tmg.flashback.notifications.usecases

import tmg.flashback.notifications.manager.NotificationManager
import tmg.flashback.notifications.repositories.NotificationRepository

interface LocalNotificationsCancelUseCase {
    fun cancel(notificationId: String)
    fun cancelAll()
}

internal class LocalNotificationsCancelUseCaseImpl(
    private val notificationManager: NotificationManager,
    private val notificationRepository: NotificationRepository,
): LocalNotificationsCancelUseCase {
    override fun cancel(notificationId: String) {
        notificationManager.cancel(notificationId)
    }

    override fun cancelAll() {
        notificationRepository.notificationUuids
            .forEach { cancel(it) }
    }
}