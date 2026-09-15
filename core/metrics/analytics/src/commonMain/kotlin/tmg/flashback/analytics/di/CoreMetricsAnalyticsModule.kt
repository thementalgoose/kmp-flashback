package tmg.flashback.analytics.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.analytics")
class CoreMetricsAnalyticsModule

val analyticsPlatformModule = platformModule()