package tmg.flashback.persistence.flashback.di

import org.koin.core.module.Module
import org.koin.dsl.module
import tmg.flashback.persistence.flashback.FlashbackDatabase
import tmg.flashback.persistence.flashback.FlashbackDatabaseFactory

expect fun platformModule(): Module

val persistenceFlashbackModule = module {
    single<FlashbackDatabase> {
        get<FlashbackDatabaseFactory>().createDatabase()
    }
}