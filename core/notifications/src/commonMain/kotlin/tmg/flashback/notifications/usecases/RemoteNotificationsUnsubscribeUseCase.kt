package tmg.flashback.notifications.usecases

import tmg.flashback.notifications.firebase.FirebaseMessagingService
import tmg.flashback.notifications.repositories.NotificationRepository

interface RemoteNotificationsUnsubscribeUseCase {
    suspend operator fun invoke(topic: String): Boolean
}

internal class RemoteNotificationsUnsubscribeUseCaseImpl(
    private val notificationRepository: NotificationRepository,
    private val firebaseMessagingService: FirebaseMessagingService
): RemoteNotificationsUnsubscribeUseCase {
    override suspend operator fun invoke(topic: String): Boolean {
        firebaseMessagingService.unsubscribeFromTopic(topic)
        notificationRepository.remoteNotificationTopics -= topic
        return true
    }
}