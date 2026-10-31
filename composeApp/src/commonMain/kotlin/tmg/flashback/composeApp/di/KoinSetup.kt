package tmg.flashback.composeApp.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin
import tmg.flashback.analytics.di.CoreMetricsAnalyticsModule
import tmg.flashback.composeApp.AppStartup
import tmg.flashback.configuration.di.CoreConfigurationModule
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
import tmg.flashback.feature.reactiongame.di.FeatureReactionGameModule
import tmg.flashback.feature.rss.di.FeatureRssModule
import tmg.flashback.feature.search.di.FeatureSearchModule
import tmg.flashback.feature.season.di.FeatureSeasonModule
import tmg.flashback.feature.weekend.di.FeatureWeekendModule
import tmg.flashback.flashbackapi.api.di.DataNetworkFlashbackModule
import tmg.flashback.infrastructure.di.InfrastructureModule
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.di.DataNetworkRssModule
import tmg.flashback.news.di.DataNetworkFlashbackNewsModule
import tmg.flashback.notifications.di.CoreNotificationsModule
import tmg.flashback.persistence.flashback.di.DataPersistenceFlashbackModule
import tmg.flashback.preferences.di.CorePreferencesModule
import tmg.flashback.style.di.PresentationStyleModule
import tmg.flashback.ui.di.PresentationUiModule
import tmg.flashback.webbrowser.di.CoreWebBrowserModule
import tmg.flashback.widgets.upnext.di.FeatureWidgetUpNextModule

@KoinApplication(
    modules = [
        ComposeAppModule::class,
        CoreConfigurationModule::class,
        CoreDeviceModule::class,
        CoreMetricsAnalyticsModule::class,
        CoreMetricsCrashlyticsModule::class,
        CoreNotificationsModule::class,
        CorePreferencesModule::class,
        CoreWebBrowserModule::class,
        DataFlashbackModule::class,
        DataNetworkFlashbackModule::class,
        DataNetworkFlashbackNewsModule::class,
        DataNetworkRssModule::class,
        DataPersistenceFlashbackModule::class,
        EasterEggsModule::class,
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
        FeatureReactionGameModule::class,
        FeatureRssModule::class,
        FeatureSearchModule::class,
        FeatureSeasonModule::class,
        FeatureWeekendModule::class,
        FeatureWidgetUpNextModule::class,
        InfrastructureModule::class,
        PresentationStyleModule::class,
        PresentationUiModule::class,
    ]
)
object FlashbackKoinApp

@Module
@ComponentScan("tmg.flashback.composeApp")
@Configuration
class ComposeAppModule

fun doInitKoin() {
    doInitKoin { }
}

fun doInitKoin(platformModules: KoinAppDeclaration) {
    logInfo("Initialising Koin")
    startKoin<FlashbackKoinApp> {
        platformModules(this)
        this.koin.get<AppStartup>().start()
    }
}