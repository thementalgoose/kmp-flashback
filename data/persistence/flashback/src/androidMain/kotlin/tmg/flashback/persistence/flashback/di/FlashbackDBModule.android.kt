package tmg.flashback.persistence.flashback.di

import org.koin.dsl.module
import tmg.flashback.persistence.flashback.FlashbackDatabaseFactory

actual fun platformModule() = module {
    single { FlashbackDatabaseFactory(get()) }
}