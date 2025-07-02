package tmg.flashback.notifications.usecases

import tmg.flashback.notifications.repositories.NotificationRepository

interface RemoteNotificationsSubscribeUseCase {
    suspend operator fun invoke(topic: String): Boolean
}

internal class RemoteNotificationsSubscribeUseCaseImpl(
    private val notificationRepository: NotificationRepository
): RemoteNotificationsSubscribeUseCase {
    override suspend operator fun invoke(topic: String): Boolean {
        notificationRepository.remoteNotificationTopics += topic
        return true
    }
}