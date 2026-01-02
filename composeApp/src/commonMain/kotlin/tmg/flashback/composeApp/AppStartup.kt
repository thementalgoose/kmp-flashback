package tmg.flashback.composeApp

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
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.infrastructure.device.log
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import tmg.flashback.composeApp.usecases.StoreFirebaseInstallationIdUseCase

/**
 * App startup class
 *
 * Called on all platforms right at the end of koin initialisation
 */
@OptIn(DelicateCoroutinesApi::class)
class AppStartup(
    private val initialiseConfigUseCase: InitialiseConfigUseCase,
    private val fetchConfigUseCase: FetchConfigUseCase,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase,
    private val deviceRepository: DeviceRepository,
    private val storeFirebaseInstallationIdUseCase: StoreFirebaseInstallationIdUseCase,
    private val initialiseCrashlyticsUseCase: InitialiseCrashlyticsUseCase,
    private val initialiseAnalyticsUseCase: InitialiseAnalyticsUseCase,
    private val currentSeasonHolder: CurrentSeasonHolder
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
            currentSeasonHolder.refresh()
        }

        // Installations
        GlobalScope.launch {
            storeFirebaseInstallationIdUseCase()
        }

        // Subscribe to upcoming topics
        GlobalScope.launch {
            scheduleUpcomingNotificationsUseCase.invoke(false)
        }

        Device.log()
    }
}