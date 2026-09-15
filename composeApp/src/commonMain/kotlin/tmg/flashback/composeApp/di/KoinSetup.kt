package tmg.flashback.composeApp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.plugin.module.dsl.modules
import tmg.flashback.composeApp.AppStartup
import tmg.flashback.analytics.di.CoreMetricsAnalyticsModule
import tmg.flashback.analytics.di.analyticsPlatformModule
import tmg.flashback.configuration.di.ConfigurationModule
import tmg.flashback.configuration.di.configurationPlatformModule
import tmg.flashback.crashlytics.di.CoreMetricsCrashlyticsModule
import tmg.flashback.crashlytics.di.crashlyticsPlatformModule
import tmg.flashback.data.repo.di.DataFlashbackModule
import tmg.flashback.device.di.CoreDeviceModule
import tmg.flashback.eastereggs.di.EasterEggsModule
import tmg.flashback.feature.about.di.FeatureAboutModule
import tmg.flashback.feature.circuits.di.FeatureCircuitsModule
import tmg.flashback.feature.constructors.di.FeatureConstructorsModule
import tmg.flashback.feature.glossary.di.FeatureGlossaryModule
import tmg.flashback.feature.lineup.di.FeatureLineupModule
import tmg.flashback.feature.drivers.di.FeatureDriversModule
import tmg.flashback.feature.maintenance.di.FeatureMaintenanceModule
import tmg.flashback.feature.notifications.di.FeatureNotificationsModule
import tmg.flashback.feature.privacypolicy.di.FeaturePrivacyPolicyModule
import tmg.flashback.feature.reactiongame.di.FeatureReactionModule
import tmg.flashback.feature.rss.di.FeatureRssModule
import tmg.flashback.feature.search.di.FeatureSearchModule
import tmg.flashback.feature.season.di.FeatureSeasonModule
import tmg.flashback.feature.weekend.di.FeatureWeekendModule
import tmg.flashback.flashbackapi.api.di.FlashbackApiModule
import tmg.flashback.feature.highlights.di.FeatureHighlightsModule
import tmg.flashback.infrastructure.di.InfrastructureModule
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.di.DataNetworkRssModule
import tmg.flashback.news.di.FlashbackNewsModule
import tmg.flashback.notifications.di.CoreNotificationsModule
import tmg.flashback.notifications.di.notificationsPlatformModule
import tmg.flashback.persistence.flashback.di.FlashbackDBModule
import tmg.flashback.persistence.flashback.di.persistencePlatformModule
import tmg.flashback.preferences.di.CorePreferencesModule
import tmg.flashback.preferences.di.preferencesPlatformModule
import tmg.flashback.style.di.PresentationStyleModule
import tmg.flashback.ui.di.PresentationUiModule
import tmg.flashback.ui.di.presentationUiPlatformModule
import tmg.flashback.webbrowser.di.CoreWebBrowserModule
import tmg.flashback.webbrowser.di.webBrowserPlatformModule
import tmg.flashback.widgets.upnext.di.FeatureWidgetUpNextModule
import tmg.flashback.widgets.upnext.di.widgetUpNextPlatformModule

fun doInitKoin() {
    doInitKoin { }
}
fun doInitKoin(platformModules: KoinApplication.() -> Unit) {
    logInfo("Initialising Koin")
    startKoin {
        platformModules(this)
        modules(ConfigurationModule::class)
        modules(configurationPlatformModule)
        modules(CoreDeviceModule::class)
        modules(CoreMetricsCrashlyticsModule::class)
        modules(crashlyticsPlatformModule)
        modules(CoreMetricsAnalyticsModule::class)
        modules(analyticsPlatformModule)
        modules(CoreNotificationsModule::class)
        modules(notificationsPlatformModule)
        modules(CorePreferencesModule::class)
        modules(CoreWebBrowserModule::class)
        modules(preferencesPlatformModule)
        modules(webBrowserPlatformModule)

        modules(DataFlashbackModule::class)
        modules(FlashbackApiModule::class)
        modules(FlashbackNewsModule::class)
        modules(DataNetworkRssModule::class)
        modules(FlashbackDBModule::class)
        modules(persistencePlatformModule)

        modules(EasterEggsModule::class)

        modules(InfrastructureModule::class)

        modules(FeatureAboutModule::class)
        modules(FeatureCircuitsModule::class)
        modules(FeatureConstructorsModule::class)
        modules(FeatureDriversModule::class)
        modules(FeatureGlossaryModule::class)
        modules(FeatureHighlightsModule::class)
        modules(FeatureLineupModule::class)
        modules(FeatureMaintenanceModule::class)
        modules(FeatureNotificationsModule::class)
        modules(FeaturePrivacyPolicyModule::class)
        modules(FeatureReactionModule::class)
        modules(FeatureRssModule::class)
        modules(FeatureSearchModule::class)
        modules(FeatureSeasonModule::class)
        modules(FeatureWeekendModule::class)
        modules(FeatureWidgetUpNextModule::class)
        modules(widgetUpNextPlatformModule)

        modules(PresentationStyleModule::class)
        modules(PresentationUiModule::class)
        modules(presentationUiPlatformModule)

        modules(ComposeAppModule::class)
        modules(platformModule())

        this.koin.get<AppStartup>().start()
    }
}

expect fun platformModule(): Module

