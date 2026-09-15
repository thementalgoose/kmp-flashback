package tmg.flashback.composeApp.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.analytics.di.CoreMetricsAnalyticsModule
import tmg.flashback.configuration.di.ConfigurationModule
import tmg.flashback.crashlytics.di.CoreMetricsCrashlyticsModule
import tmg.flashback.data.repo.di.DataFlashbackModule
import tmg.flashback.device.di.CoreDeviceModule
import tmg.flashback.eastereggs.di.EasterEggsModule
import tmg.flashback.feature.about.di.FeatureAboutModule
import tmg.flashback.feature.circuits.di.FeatureCircuitsModule
import tmg.flashback.feature.constructors.di.FeatureConstructorsModule
import tmg.flashback.feature.drivers.di.FeatureDriversModule
import tmg.flashback.feature.glossary.di.FeatureGlossaryModule
import tmg.flashback.feature.highlights.di.FeatureHighlightsModule
import tmg.flashback.feature.lineup.di.FeatureLineupModule
import tmg.flashback.feature.maintenance.di.FeatureMaintenanceModule
import tmg.flashback.feature.notifications.di.FeatureNotificationsModule
import tmg.flashback.feature.privacypolicy.di.FeaturePrivacyPolicyModule
import tmg.flashback.feature.reactiongame.di.FeatureReactionModule
import tmg.flashback.feature.rss.di.FeatureRssModule
import tmg.flashback.feature.search.di.FeatureSearchModule
import tmg.flashback.feature.season.di.FeatureSeasonModule
import tmg.flashback.feature.weekend.di.FeatureWeekendModule
import tmg.flashback.widgets.upnext.di.FeatureWidgetUpNextModule
import tmg.flashback.infrastructure.di.InfrastructureModule
import tmg.flashback.news.di.FlashbackNewsModule
import tmg.flashback.network.rss.di.DataNetworkRssModule
import tmg.flashback.notifications.di.CoreNotificationsModule
import tmg.flashback.persistence.flashback.di.FlashbackDBModule
import tmg.flashback.preferences.di.CorePreferencesModule
import tmg.flashback.style.di.PresentationStyleModule
import tmg.flashback.ui.di.PresentationUiModule
import tmg.flashback.webbrowser.di.CoreWebBrowserModule

@Module(includes = [
	ConfigurationModule::class,
	CoreDeviceModule::class,
	CoreMetricsCrashlyticsModule::class,
	CoreMetricsAnalyticsModule::class,
	CoreNotificationsModule::class,
	CorePreferencesModule::class,
	CoreWebBrowserModule::class,
	DataFlashbackModule::class,
	DataNetworkRssModule::class,
	FlashbackNewsModule::class,
	FlashbackDBModule::class,
	EasterEggsModule::class,
	InfrastructureModule::class,
	FeatureAboutModule::class,
	FeatureCircuitsModule::class,
	FeatureConstructorsModule::class,
	FeatureDriversModule::class,
	FeatureGlossaryModule::class,
	FeatureHighlightsModule::class,
	FeatureLineupModule::class,
	FeatureMaintenanceModule::class,
	FeatureNotificationsModule::class,
	FeaturePrivacyPolicyModule::class,
	FeatureReactionModule::class,
	FeatureRssModule::class,
	FeatureSearchModule::class,
	FeatureSeasonModule::class,
	FeatureWeekendModule::class,
	FeatureWidgetUpNextModule::class,
	PresentationStyleModule::class,
	PresentationUiModule::class,
])
@ComponentScan("tmg.flashback.composeApp")
class ComposeAppModule