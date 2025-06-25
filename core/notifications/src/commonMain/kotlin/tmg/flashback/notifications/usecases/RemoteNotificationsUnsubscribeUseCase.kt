package tmg.flashback.notifications.usecases

import com.mmk.kmpnotifier.notification.NotifierManager
import tmg.flashback.notifications.repositories.NotificationRepository

interface RemoteNotificationsUnsubscribeUseCase {
    suspend operator fun invoke(topic: String): Boolean
}

internal class RemoteNotificationsUnsubscribeUseCaseImpl(
    private val notificationRepository: NotificationRepository
): RemoteNotificationsUnsubscribeUseCase {
    override suspend operator fun invoke(topic: String): Boolean {
        NotifierManager.getPushNotifier().unSubscribeFromTopic(topic)
        notificationRepository.remoteNotificationTopics -= topic
        return true
    }
}