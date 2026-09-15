package tmg.flashback.feature.about.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.analytics.di.CoreMetricsAnalyticsModule
import tmg.flashback.device.di.CoreDeviceModule
import tmg.flashback.notifications.di.CoreNotificationsModule

@Module(includes = [CoreMetricsAnalyticsModule::class, CoreDeviceModule::class, CoreNotificationsModule::class])
@ComponentScan("tmg.flashback.feature.about")
class FeatureAboutModule