package tmg.flashback.crashlytics.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.crashlytics")
class CoreMetricsCrashlyticsModule

val crashlyticsPlatformModule = platformModule()