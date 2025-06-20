package tmg.flashback.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import tmg.flashback.configuration.di.configurationModules
import tmg.flashback.flashbackapi.api.di.dataNetworkFlashbackModules
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.persistence.flashback.di.dataPersistenceFlashbackModules

fun initKoin(platformModules: KoinApplication.() -> Unit = { }) {
    logInfo("Initialising Koin")
    startKoin {
        platformModules(this)
        modules(configurationModules)
        modules(dataNetworkFlashbackModules)
        modules(dataPersistenceFlashbackModules)
    }
}