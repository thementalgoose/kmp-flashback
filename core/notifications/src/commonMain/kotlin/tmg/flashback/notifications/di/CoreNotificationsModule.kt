package tmg.flashback.notifications.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.preferences.di.CorePreferencesModule

@Module(includes = [CorePreferencesModule::class])
@ComponentScan("tmg.flashback.notifications")
class CoreNotificationsModule

val notificationsPlatformModule = platformModule()
