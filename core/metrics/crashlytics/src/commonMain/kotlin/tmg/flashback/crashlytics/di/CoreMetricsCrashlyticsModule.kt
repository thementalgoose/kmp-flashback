package tmg.flashback.crashlytics.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.crashlytics")
@Configuration
class CoreMetricsCrashlyticsModule