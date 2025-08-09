package tmg.flashback.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.AppStartup
import tmg.flashback.analytics.di.coreMetricsAnalyticsModule
import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import tmg.flashback.configuration.di.coreConfigurationModule
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService
import tmg.flashback.crashlytics.di.coreMetricsCrashlyticsModule
import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService
import tmg.flashback.data.repo.di.dataFlashbackModule
import tmg.flashback.device.di.coreDeviceModules
import tmg.flashback.eastereggs.di.easterEggsModule
import tmg.flashback.feature.about.di.featureAboutModule
import tmg.flashback.feature.circuits.di.featureCircuitsModule
import tmg.flashback.feature.constructors.di.featureConstructorsModule
import tmg.flashback.feature.drivers.di.featureDriversModule
import tmg.flashback.feature.maintenance.di.featureMaintenanceModule
import tmg.flashback.feature.notifications.di.featureNotificationsModule
import tmg.flashback.feature.privacypolicy.di.featurePrivacyPolicyModule
import tmg.flashback.feature.reactiongame.di.featureReactionGameModule
import tmg.flashback.feature.rss.di.featureRssModule
import tmg.flashback.feature.search.di.featureSearchModule
import tmg.flashback.feature.season.di.featureSeasonModule
import tmg.flashback.feature.weekend.di.featureWeekendModule
import tmg.flashback.firebase.FirebaseAnalyticsServiceImpl
import tmg.flashback.firebase.FirebaseCrashlyticsServiceImpl
import tmg.flashback.firebase.FirebaseMessagingServiceImpl
import tmg.flashback.firebase.FirebaseRemoteConfigServiceImpl
import tmg.flashback.flashbackapi.api.di.dataNetworkFlashbackModule
import tmg.flashback.feature.highlights.di.featureHighlightsModule
import tmg.flashback.infrastructure.di.infrastructureModule
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.di.dataNetworkRssModule
import tmg.flashback.news.di.dataNetworkFlashbackNewsModule
import tmg.flashback.notifications.di.coreNotificationsModule
import tmg.flashback.notifications.firebase.FirebaseMessagingService
import tmg.flashback.persistence.flashback.di.dataPersistenceFlashbackModule
import tmg.flashback.preferences.di.corePreferencesModule
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.presentation.settings.AllSettingsViewModel
import tmg.flashback.presentation.settings.about.SettingsAboutViewModel
import tmg.flashback.presentation.settings.browser.SettingsBrowserViewModel
import tmg.flashback.presentation.settings.darkmode.SettingsDarkModeViewModel
import tmg.flashback.presentation.settings.layout.SettingsLayoutViewModel
import tmg.flashback.presentation.settings.notifications.results.SettingsNotificationResultsViewModel
import tmg.flashback.presentation.settings.notifications.upcoming.SettingsNotificationUpcomingViewModel
import tmg.flashback.presentation.settings.privacy.SettingsPrivacyViewModel
import tmg.flashback.presentation.settings.theme.SettingsThemeViewModel
import tmg.flashback.presentation.settings.weather.SettingsWeatherViewModel
import tmg.flashback.presentation.settings.widgets.SettingsWidgetsViewModel
import tmg.flashback.presentation.sync.SyncViewModel
import tmg.flashback.repositories.OnboardingRepository
import tmg.flashback.repositories.OnboardingRepositoryImpl
import tmg.flashback.style.di.presentationStyleModule
import tmg.flashback.ui.di.presentationUiModule
import tmg.flashback.usecases.RequiresSyncUseCase
import tmg.flashback.usecases.RequiresSyncUseCaseImpl
import tmg.flashback.webbrowser.di.coreWebBrowserModule
import tmg.flashback.widgets.upnext.di.featureWidgetUpNextModule

fun doInitKoin() {
    doInitKoin { }
}
fun doInitKoin(platformModules: KoinApplication.() -> Unit) {
    logInfo("Initialising Koin")
    startKoin {
        platformModules(this)
        modules(coreConfigurationModule)
        modules(coreDeviceModules)
        modules(coreMetricsCrashlyticsModule)
        modules(coreMetricsAnalyticsModule)
        modules(coreNotificationsModule)
        modules(corePreferencesModule)
        modules(coreWebBrowserModule)

        modules(dataFlashbackModule)
        modules(dataNetworkFlashbackModule)
        modules(dataNetworkFlashbackNewsModule)
        modules(dataNetworkRssModule)
        modules(dataPersistenceFlashbackModule)

        modules(easterEggsModule)

        modules(infrastructureModule)

        modules(featureAboutModule)
        modules(featureCircuitsModule)
        modules(featureConstructorsModule)
        modules(featureDriversModule)
        modules(featureHighlightsModule)
        modules(featureMaintenanceModule)
        modules(featureNotificationsModule)
        modules(featurePrivacyPolicyModule)
        modules(featureReactionGameModule)
        modules(featureRssModule)
        modules(featureSearchModule)
        modules(featureSeasonModule)
        modules(featureWeekendModule)
        modules(featureWidgetUpNextModule)

        modules(presentationStyleModule)
        modules(presentationUiModule)

        modules(module())
        modules(platformModule())
        modules(firebaseModule())

        this.koin.get<AppStartup>().start()
    }
}

expect fun platformModule(): Module

internal fun module() = module {

    single { AppStartup(get(), get(), get(),get(), get(), get(), get()) }

    viewModel { AppNavigationViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }

    single<RequiresSyncUseCase> { RequiresSyncUseCaseImpl(get(), get()) }

    viewModel { AllSettingsViewModel(get(), get(), get()) }
    viewModel { SettingsDarkModeViewModel(get()) }
    viewModel { SettingsThemeViewModel(get()) }
    viewModel { SettingsLayoutViewModel(get(), get()) }
    viewModel { SettingsWeatherViewModel(get()) }
    viewModel { SettingsBrowserViewModel(get()) }
    viewModel { SettingsAboutViewModel(get(), get()) }
    viewModel { SettingsPrivacyViewModel(get(), get()) }
    viewModel { SettingsWidgetsViewModel(get()) }
    viewModel { SettingsNotificationUpcomingViewModel(get(), get(), get(), get(), get()) }
    viewModel { SettingsNotificationResultsViewModel(get(), get(), get(), get()) }

    viewModel { SyncViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}

internal fun firebaseModule() = module {
    singleOf<FirebaseRemoteConfigService>(::FirebaseRemoteConfigServiceImpl)
    singleOf<FirebaseCrashlyticsService>(::FirebaseCrashlyticsServiceImpl)
    singleOf<FirebaseAnalyticsService>(::FirebaseAnalyticsServiceImpl)
    singleOf<FirebaseMessagingService>(::FirebaseMessagingServiceImpl)
}