package tmg.flashback

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase

/**
 * App startup class
 *
 * Called on all platforms right at the end of koin initialisation
 */
@OptIn(DelicateCoroutinesApi::class)
class AppStartup(
    private val configManager: ConfigManager,
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase
) {
    fun start() {
        // Remote config
        configManager.initialiseRemoteConfig(RemoteConfigDefaults.defaults)

        // Subscribe to remote topics
        GlobalScope.launch {
            scheduleUpcomingNotificationsUseCase.invoke(false)
            subscribeResultNotificationsUseCase.invoke()
        }
    }
}