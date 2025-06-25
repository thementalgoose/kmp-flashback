package tmg.flashback.notifications.usecases

import com.mmk.kmpnotifier.notification.NotifierManager
import tmg.flashback.notifications.repositories.NotificationRepository

interface LocalNotificationsCancelUseCase {
    fun cancel(notificationId: Int)
    fun cancelAll()
}

internal class LocalNotificationsCancelUseCaseImpl(
    private val notificationRepository: NotificationRepository
): LocalNotificationsCancelUseCase {
    override fun cancel(notificationId: Int) {
        NotifierManager.getLocalNotifier().remove(notificationId)
        notificationRepository.notificationIds -= notificationId
    }

    override fun cancelAll() {
        NotifierManager.getLocalNotifier().removeAll()
        notificationRepository.notificationIds = emptySet()
    }
}