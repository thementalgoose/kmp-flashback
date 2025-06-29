package tmg.flashback.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.analytics.di.coreMetricsAnalyticsModule
import tmg.flashback.configuration.di.coreConfigurationModule
import tmg.flashback.crashlytics.di.coreMetricsCrashlyticsModule
import tmg.flashback.data.repo.di.dataFlashbackModule
import tmg.flashback.eastereggs.di.easterEggsModule
import tmg.flashback.feature.about.di.featureAboutModule
import tmg.flashback.feature.circuits.di.featureCircuitsModule
import tmg.flashback.feature.constructors.di.featureConstructorsModule
import tmg.flashback.feature.drivers.di.featureDriversModule
import tmg.flashback.feature.maintenance.di.featureMaintenanceModule
import tmg.flashback.feature.privacypolicy.di.featurePrivacyPolicyModule
import tmg.flashback.feature.reactiongame.di.featureReactionGameModule
import tmg.flashback.feature.rss.di.featureRssModule
import tmg.flashback.feature.search.di.featureSearchModule
import tmg.flashback.feature.season.di.featureSeasonModule
import tmg.flashback.feature.weekend.di.featureWeekendModule
import tmg.flashback.flashbackapi.api.di.dataNetworkFlashbackModule
import tmg.flashback.infrastructure.di.infrastructureModule
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.di.dataNetworkRssModule
import tmg.flashback.persistence.flashback.di.dataPersistenceFlashbackModule
import tmg.flashback.preferences.di.corePreferencesModule
import tmg.flashback.presentation.AppContainerViewModel
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.presentation.settings.AllSettingsViewModel
import tmg.flashback.presentation.settings.about.SettingsAboutViewModel
import tmg.flashback.presentation.settings.browser.SettingsBrowserViewModel
import tmg.flashback.presentation.settings.darkmode.SettingsDarkModeViewModel
import tmg.flashback.presentation.settings.layout.SettingsLayoutViewModel
import tmg.flashback.presentation.settings.privacy.SettingsPrivacyViewModel
import tmg.flashback.presentation.settings.theme.SettingsThemeScreen
import tmg.flashback.presentation.settings.theme.SettingsThemeViewModel
import tmg.flashback.presentation.settings.weather.SettingsWeatherViewModel
import tmg.flashback.presentation.sync.SyncViewModel
import tmg.flashback.ui.di.presentationUiModule
import tmg.flashback.webbrowser.di.coreWebBrowserModule
import tmg.flashback.widget.upnext.di.featureWidgetUpNextModule

fun doInitKoin() {
    doInitKoin { }
}
fun doInitKoin(platformModules: KoinApplication.() -> Unit) {
    logInfo("Initialising Koin")
    startKoin {
        platformModules(this)
        modules(coreConfigurationModule)
        modules(coreMetricsCrashlyticsModule)
        modules(coreMetricsAnalyticsModule)
        modules(corePreferencesModule)
        modules(coreWebBrowserModule)

        modules(dataNetworkFlashbackModule)
        modules(dataPersistenceFlashbackModule)
        modules(dataFlashbackModule)
        modules(dataNetworkRssModule)

        modules(easterEggsModule)

        modules(infrastructureModule)

        modules(featureAboutModule)
        modules(featureCircuitsModule)
        modules(featureConstructorsModule)
        modules(featureDriversModule)
        modules(featureMaintenanceModule)
        modules(featurePrivacyPolicyModule)
        modules(featureReactionGameModule)
        modules(featureRssModule)
        modules(featureSearchModule)
        modules(featureSeasonModule)
        modules(featureWeekendModule)
        modules(featureWidgetUpNextModule)

        modules(presentationUiModule)

        modules(module())
    }
}

internal fun module() = module {
    viewModel { AppContainerViewModel() }
    viewModel { AppNavigationViewModel(get(), get(), get(), get(), get(), get()) }

    viewModel { AllSettingsViewModel(get(), get(), get()) }
    viewModel { SettingsDarkModeViewModel(get()) }
    viewModel { SettingsThemeViewModel(get()) }
    viewModel { SettingsLayoutViewModel(get()) }
    viewModel { SettingsWeatherViewModel(get()) }
    viewModel { SettingsBrowserViewModel(get()) }
    viewModel { SettingsAboutViewModel() }
    viewModel { SettingsPrivacyViewModel(get(), get()) }

    viewModel { SyncViewModel(get(), get(), get(), get(), get(), get(), get()) }
}