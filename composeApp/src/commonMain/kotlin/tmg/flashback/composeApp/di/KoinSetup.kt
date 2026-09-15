package tmg.flashback.composeApp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.modules
import tmg.flashback.composeApp.AppStartup
import tmg.flashback.analytics.di.CoreMetricsAnalyticsModule
import tmg.flashback.analytics.di.analyticsPlatformModule
import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import tmg.flashback.configuration.di.ConfigurationModule
import tmg.flashback.configuration.di.configurationPlatformModule
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService
import tmg.flashback.crashlytics.di.CoreMetricsCrashlyticsModule
import tmg.flashback.crashlytics.di.crashlyticsPlatformModule
import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService
import tmg.flashback.data.repo.di.DataFlashbackModule
import tmg.flashback.device.di.CoreDeviceModule
import tmg.flashback.device.manager.UiManager
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
import tmg.flashback.composeApp.firebase.FirebaseAnalyticsServiceImpl
import tmg.flashback.composeApp.firebase.FirebaseCrashlyticsServiceImpl
import tmg.flashback.composeApp.firebase.FirebaseMessagingServiceImpl
import tmg.flashback.composeApp.firebase.FirebaseRemoteConfigServiceImpl
import tmg.flashback.flashbackapi.api.di.FlashbackApiModule
import tmg.flashback.feature.highlights.di.FeatureHighlightsModule
import tmg.flashback.composeApp.firebase.FirebaseInstallationService
import tmg.flashback.composeApp.firebase.FirebaseInstallationServiceImpl
import tmg.flashback.infrastructure.di.InfrastructureModule
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.composeApp.manager.UiManagerImpl
import tmg.flashback.network.rss.di.DataNetworkRssModule
import tmg.flashback.news.di.FlashbackNewsModule
import tmg.flashback.notifications.di.CoreNotificationsModule
import tmg.flashback.notifications.di.notificationsPlatformModule
import tmg.flashback.notifications.firebase.FirebaseMessagingService
import tmg.flashback.persistence.flashback.di.FlashbackDBModule
import tmg.flashback.persistence.flashback.di.persistencePlatformModule
import tmg.flashback.preferences.di.CorePreferencesModule
import tmg.flashback.preferences.di.preferencesPlatformModule
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.settings.AllSettingsViewModel
import tmg.flashback.composeApp.presentation.settings.about.SettingsAboutViewModel
import tmg.flashback.composeApp.presentation.settings.browser.SettingsBrowserViewModel
import tmg.flashback.composeApp.presentation.settings.darkmode.SettingsDarkModeViewModel
import tmg.flashback.composeApp.presentation.settings.layout_home.SettingsLayoutHomeViewModel
import tmg.flashback.composeApp.presentation.settings.layout_race.SettingsLayoutRaceViewModel
import tmg.flashback.composeApp.presentation.settings.notifications.results.SettingsNotificationResultsViewModel
import tmg.flashback.composeApp.presentation.settings.notifications.upcoming.SettingsNotificationUpcomingViewModel
import tmg.flashback.composeApp.presentation.settings.privacy.SettingsPrivacyViewModel
import tmg.flashback.composeApp.presentation.settings.theme.SettingsThemeViewModel
import tmg.flashback.composeApp.presentation.settings.widgets.SettingsWidgetsViewModel
import tmg.flashback.composeApp.presentation.sync.SyncViewModel
import tmg.flashback.composeApp.repositories.NavRepository
import tmg.flashback.composeApp.repositories.NavRepositoryImpl
import tmg.flashback.composeApp.repositories.OnboardingRepository
import tmg.flashback.composeApp.repositories.OnboardingRepositoryImpl
import tmg.flashback.style.di.PresentationStyleModule
import tmg.flashback.ui.di.PresentationUiModule
import tmg.flashback.ui.di.presentationUiPlatformModule
import tmg.flashback.composeApp.usecases.RequiresSyncUseCase
import tmg.flashback.composeApp.usecases.RequiresSyncUseCaseImpl
import tmg.flashback.composeApp.usecases.StoreFirebaseInstallationIdUseCase
import tmg.flashback.composeApp.usecases.StoreFirebaseInstallationIdUseCaseImpl
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
        modules(corePreferencesModule)
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
        modules(presentationUiModule)
        modules(presentationUiPlatformModule)

        modules(module())
        modules(platformModule())
        modules(firebaseModule())
        modules(inverseModule())

        this.koin.get<AppStartup>().start()
    }
}

expect fun platformModule(): Module

internal fun module() = module {

    single { AppStartup(get(), get(), get(), get(),get(), get(), get(), get()) }

    viewModel { AppNavigationViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }
    single<NavRepository> { NavRepositoryImpl(get()) }

    single<StoreFirebaseInstallationIdUseCase> { StoreFirebaseInstallationIdUseCaseImpl(get(), get()) }
    single<RequiresSyncUseCase> { RequiresSyncUseCaseImpl(get(), get()) }

    viewModel { AllSettingsViewModel(get(), get(), get()) }
    viewModel { SettingsDarkModeViewModel(get(), get()) }
    viewModel { SettingsThemeViewModel(get(), get(), get()) }
    viewModel { SettingsLayoutHomeViewModel(get(), get()) }
    viewModel { SettingsLayoutRaceViewModel(get(), get()) }
    viewModel { SettingsBrowserViewModel(get()) }
    viewModel { SettingsAboutViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SettingsPrivacyViewModel(get(), get(), get()) }
    viewModel { SettingsWidgetsViewModel(get()) }
    viewModel { SettingsNotificationUpcomingViewModel(get(), get(), get(), get(), get()) }
    viewModel { SettingsNotificationResultsViewModel(get(), get(), get(), get()) }

    viewModel { SyncViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}

internal fun inverseModule() = module {
    single<UiManager> { UiManagerImpl(get()) }
}

internal fun firebaseModule() = module {
    singleOf<FirebaseRemoteConfigService>(::FirebaseRemoteConfigServiceImpl)
    singleOf<FirebaseCrashlyticsService>(::FirebaseCrashlyticsServiceImpl)
    singleOf<FirebaseAnalyticsService>(::FirebaseAnalyticsServiceImpl)
    singleOf<FirebaseMessagingService>(::FirebaseMessagingServiceImpl)
    singleOf<FirebaseInstallationService>(::FirebaseInstallationServiceImpl)
}
