package tmg.flashback.feature.notifications.usecases

import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.notifications.repositories.NotificationRepository
import tmg.flashback.notifications.usecases.RemoteNotificationsSubscribeUseCase
import tmg.flashback.notifications.usecases.RemoteNotificationsUnsubscribeUseCase
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState

interface SubscribeResultNotificationsUseCase {
    suspend operator fun invoke()
}

internal class SubscribeResultNotificationsUseCaseImpl(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val subscribeUseCase: RemoteNotificationsSubscribeUseCase,
    private val unsubscribeUseCase: RemoteNotificationsUnsubscribeUseCase,
    private val permissionManager: PermissionManager
): SubscribeResultNotificationsUseCase {
    override suspend fun invoke() {
        if (permissionManager.getPermissionState(Permission.Notifications) != PermissionState.Granted) {
            logDebug("Notifications", "Skipping registering remote topics as permission disabled")
            return
        }

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