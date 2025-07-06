package tmg.flashback

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tmg.flashback.analytics.usecases.InitialiseAnalyticsUseCase
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.crashlytics.usecases.InitialiseCrashlyticsUseCase
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.log

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
    private val initialiseCrashlyticsUseCase: InitialiseCrashlyticsUseCase,
    private val initialiseAnalyticsUseCase: InitialiseAnalyticsUseCase,
) {
    fun start() {
        // Crashlytics
        initialiseCrashlyticsUseCase.initialise(deviceRepository.deviceUdid, emptyMap())

        // Analytics
        initialiseAnalyticsUseCase.initialise(deviceRepository.deviceUdid)

        // Remote config
        configManager.initialiseRemoteConfig(RemoteConfigDefaults.defaults)

        // Subscribe to remote topics
        GlobalScope.launch {
            scheduleUpcomingNotificationsUseCase.invoke(false)
            subscribeResultNotificationsUseCase.invoke()
        }

        Device.log()
    }
}