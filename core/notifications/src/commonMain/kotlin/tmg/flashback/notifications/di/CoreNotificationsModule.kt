package tmg.flashback.notifications.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.notifications")
class CoreNotificationsModule

val notificationsPlatformModule = platformModule()
