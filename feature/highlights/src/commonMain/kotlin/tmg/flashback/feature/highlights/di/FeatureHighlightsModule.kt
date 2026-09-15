package tmg.flashback.feature.highlights.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.analytics.di.CoreMetricsAnalyticsModule
import tmg.flashback.device.di.CoreDeviceModule
import tmg.flashback.news.di.FlashbackNewsModule
import tmg.flashback.preferences.di.CorePreferencesModule

@Module(includes = [CoreMetricsAnalyticsModule::class, CoreDeviceModule::class, FlashbackNewsModule::class, CorePreferencesModule::class])
@ComponentScan("tmg.flashback.feature.highlights")
class FeatureHighlightsModule