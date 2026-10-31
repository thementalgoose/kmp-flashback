package tmg.flashback.feature.notifications.usecases

import org.koin.core.annotation.Single
import tmg.flashback.feature.notifications.model.NotificationNews
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.notifications.usecases.RemoteNotificationsSubscribeUseCase
import tmg.flashback.notifications.usecases.RemoteNotificationsUnsubscribeUseCase
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState

interface SubscribeNewsNotificationsUseCase {
    suspend operator fun invoke()
}

@Single
internal class SubscribeNewsNotificationsUseCaseImpl(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val subscribeUseCase: RemoteNotificationsSubscribeUseCase,
    private val unsubscribeUseCase: RemoteNotificationsUnsubscribeUseCase,
    private val permissionManager: PermissionManager
): SubscribeNewsNotificationsUseCase {
    override suspend fun invoke() {
        if (permissionManager.getPermissionState(Permission.Notifications) != PermissionState.Granted) {
            logDebug("Notifications", "Skipping registering remote topics as permission disabled")
            return
        }

        val results = notificationSettingsRepository.notificationNewsEnabled
        NotificationNews.entries.forEach {
            if (results.contains(it)) {
                subscribeUseCase(it.remoteSubscriptionTopic)
            } else {
                unsubscribeUseCase(it.remoteSubscriptionTopic)
            }
        }
    }
}