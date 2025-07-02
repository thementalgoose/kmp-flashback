package tmg.flashback.feature.notifications.usecases

import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.notifications.repositories.NotificationRepository
import tmg.flashback.notifications.usecases.RemoteNotificationsSubscribeUseCase

interface SubscribeResultNotificationsUseCase {
    suspend operator fun invoke()
}

internal class SubscribeResultNotificationsUseCaseImpl(
    private val notificationRepository: NotificationRepository,
    private val remoteNotificationsUseCase: RemoteNotificationsSubscribeUseCase
): SubscribeResultNotificationsUseCase {
    override suspend fun invoke() {
        NotificationResultsAvailable.entries.forEach {
            remoteNotificationsUseCase(it.remoteSubscriptionTopic)
        }
    }
}