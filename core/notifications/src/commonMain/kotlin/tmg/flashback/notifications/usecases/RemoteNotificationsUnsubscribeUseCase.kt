package tmg.flashback.notifications.usecases

import org.koin.core.annotation.Single
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.notifications.firebase.FirebaseMessagingService
import tmg.flashback.notifications.repositories.NotificationRepository

interface RemoteNotificationsUnsubscribeUseCase {
    suspend operator fun invoke(topic: String): Boolean
}

@Single
internal class RemoteNotificationsUnsubscribeUseCaseImpl(
    private val notificationRepository: NotificationRepository,
    private val firebaseMessagingService: FirebaseMessagingService
): RemoteNotificationsUnsubscribeUseCase {
    override suspend operator fun invoke(topic: String): Boolean {
        logDebug("Notifications", "Unsubscribing to topic $topic")
        firebaseMessagingService.unsubscribeFromTopic(topic)
        notificationRepository.remoteNotificationTopics -= topic
        return true
    }
}