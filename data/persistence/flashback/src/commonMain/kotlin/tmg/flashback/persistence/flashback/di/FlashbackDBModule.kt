package tmg.flashback.persistence.flashback.di

import org.koin.dsl.module
import tmg.flashback.persistence.flashback.FlashbackDatabase
import tmg.flashback.persistence.flashback.FlashbackDatabaseFactory

val dataPersistenceFlashbackModules = listOf(
    platformModule(),
    module()
)

internal fun module() = module {
    single<FlashbackDatabase> {
        get<FlashbackDatabaseFactory>().createDatabase()
    }
}