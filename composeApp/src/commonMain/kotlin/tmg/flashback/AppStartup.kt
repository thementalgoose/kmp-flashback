package tmg.flashback

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.crashlytics.usecases.InitialiseCrashlyticsUseCase
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.logDebug
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.repositories.DeviceRepository

/**
 * App startup class
 *
 * Called on all platforms right at the end of koin initialisation
 */
@OptIn(DelicateCoroutinesApi::class)
class AppStartup(
    private val configManager: ConfigManager,
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase,
    private val deviceRepository: DeviceRepository,
    private val initialiseCrashlyticsUseCase: InitialiseCrashlyticsUseCase
) {
    fun start() {
        // Crashlytics
        initialiseCrashlyticsUseCase.initialise(deviceRepository.deviceUdid, emptyMap())

        // Remote config
        configManager.initialiseRemoteConfig(RemoteConfigDefaults.defaults)

        // Subscribe to remote topics
        GlobalScope.launch {
            scheduleUpcomingNotificationsUseCase.invoke(false)
            subscribeResultNotificationsUseCase.invoke()
        }

        Device.logDebug()
    }
}