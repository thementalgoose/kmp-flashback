package tmg.flashback.feature.notifications.usecases

import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.notifications.manager.NotificationManager

interface IsNotificationEnabledUseCase {
    operator fun invoke(upcoming: NotificationUpcoming): Boolean
    operator fun invoke(resultsAvailable: NotificationResultsAvailable): Boolean
}

internal class IsNotificationEnabledUseCaseImpl(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val notificationManager: NotificationManager,
): IsNotificationEnabledUseCase {
    override fun invoke(upcoming: NotificationUpcoming): Boolean {
        val isSettingEnabled = notificationSettingsRepository.notificationUpcomingEnabled.contains(upcoming)
        val isChannelEnabled = notificationManager.isChannelActive(upcoming.channelId)
        return isSettingEnabled && isChannelEnabled
    }

    override fun invoke(resultsAvailable: NotificationResultsAvailable): Boolean {
        val isSettingEnabled = notificationSettingsRepository.notificationResultsEnabled.contains(resultsAvailable)
        val isChannelEnabled = notificationManager.isChannelActive(resultsAvailable.channelId)
        return isSettingEnabled && isChannelEnabled
    }
}
