package tmg.flashback

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tmg.flashback.analytics.usecases.InitialiseAnalyticsUseCase
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.usecases.FetchConfigUseCase
import tmg.flashback.configuration.usecases.InitialiseConfigUseCase
import tmg.flashback.crashlytics.usecases.InitialiseCrashlyticsUseCase
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.infrastructure.device.log

/**
 * App startup class
 *
 * Called on all platforms right at the end of koin initialisation
 */
@OptIn(DelicateCoroutinesApi::class)
class AppStartup(
    private val initialiseConfigUseCase: InitialiseConfigUseCase,
    private val fetchConfigUseCase: FetchConfigUseCase,
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
        initialiseConfigUseCase(RemoteConfigDefaults.defaults)
        GlobalScope.launch {
            fetchConfigUseCase.fetchAndApply()
        }

        // Subscribe to remote topics
        if (Device.platform == Platform.Android) {
            GlobalScope.launch {
                scheduleUpcomingNotificationsUseCase.invoke(false)
                subscribeResultNotificationsUseCase.invoke()
            }
        }

        Device.log()
    }
}