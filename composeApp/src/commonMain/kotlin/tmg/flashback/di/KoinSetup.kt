package tmg.flashback.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import tmg.flashback.analytics.di.coreMetricsAnalyticsModule
import tmg.flashback.configuration.di.coreConfigurationModule
import tmg.flashback.crashlytics.di.coreMetricsCrashlyticsModule
import tmg.flashback.data.repo.di.dataFlashbackModule
import tmg.flashback.feature.season.di.featureSeasonModule
import tmg.flashback.flashbackapi.api.di.dataNetworkFlashbackModule
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.persistence.flashback.di.dataPersistenceFlashbackModule
import tmg.flashback.preferences.di.corePreferencesModule
import tmg.flashback.ui.di.presentationUiModule

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
        modules(dataNetworkFlashbackModule)
        modules(dataPersistenceFlashbackModule)
        modules(dataFlashbackModule)

        modules(featureSeasonModule)

        modules(presentationUiModule)
    }
}