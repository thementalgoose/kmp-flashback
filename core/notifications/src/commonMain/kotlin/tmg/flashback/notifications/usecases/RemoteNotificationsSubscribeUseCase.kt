package tmg.flashback.notifications.usecases

import org.koin.core.annotation.Single
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.notifications.firebase.FirebaseMessagingService
import tmg.flashback.notifications.repositories.NotificationRepository

interface RemoteNotificationsSubscribeUseCase {
    suspend operator fun invoke(topic: String): Boolean
}

@Single
internal class RemoteNotificationsSubscribeUseCaseImpl(
    private val notificationRepository: NotificationRepository,
    private val firebaseMessagingService: FirebaseMessagingService
): RemoteNotificationsSubscribeUseCase {
    override suspend operator fun invoke(topic: String): Boolean {
        logDebug("Notifications", "Subscribing to topic $topic")
        firebaseMessagingService.subscribeToTopic(topic)
        notificationRepository.remoteNotificationTopics += topic
        return true
    }
}