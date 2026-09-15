package tmg.flashback.feature.notifications.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.data.repo.di.DataFlashbackModule
import tmg.flashback.device.di.CoreDeviceModule
import tmg.flashback.notifications.di.CoreNotificationsModule
import tmg.flashback.preferences.di.CorePreferencesModule
import tmg.flashback.ui.di.PresentationUiModule

@Module(includes = [DataFlashbackModule::class, CoreDeviceModule::class, CoreNotificationsModule::class, CorePreferencesModule::class, PresentationUiModule::class])
@ComponentScan("tmg.flashback.feature.notifications")
class FeatureNotificationsModule
