package tmg.flashback.notifications.usecases

import org.koin.core.annotation.Single
import tmg.flashback.notifications.manager.NotificationManager
import tmg.flashback.notifications.repositories.NotificationRepository

interface LocalNotificationsCancelUseCase {
    fun cancel(notificationId: Int)
    fun cancelAll()
}

@Single
internal class LocalNotificationsCancelUseCaseImpl(
    private val notificationManager: NotificationManager,
    private val notificationRepository: NotificationRepository,
): LocalNotificationsCancelUseCase {
    override fun cancel(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    override fun cancelAll() {
        notificationManager.cancelAll(notificationRepository.notificationIds.toList())
    }
}