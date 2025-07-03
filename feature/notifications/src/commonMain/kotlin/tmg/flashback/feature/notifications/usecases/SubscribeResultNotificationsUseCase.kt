package tmg.flashback.feature.notifications.usecases

import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.notifications.repositories.NotificationRepository
import tmg.flashback.notifications.usecases.RemoteNotificationsSubscribeUseCase
import tmg.flashback.notifications.usecases.RemoteNotificationsUnsubscribeUseCase

interface SubscribeResultNotificationsUseCase {
    suspend operator fun invoke()
}

internal class SubscribeResultNotificationsUseCaseImpl(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val subscribeUseCase: RemoteNotificationsSubscribeUseCase,
    private val unsubscribeUseCase: RemoteNotificationsUnsubscribeUseCase
): SubscribeResultNotificationsUseCase {
    override suspend fun invoke() {
        val results = notificationSettingsRepository.notificationResultsEnabled
        NotificationResultsAvailable.entries.forEach {
            if (results.contains(it)) {
                subscribeUseCase(it.remoteSubscriptionTopic)
            } else {
                unsubscribeUseCase(it.remoteSubscriptionTopic)
            }
        }
    }
}