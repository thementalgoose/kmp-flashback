package tmg.flashback.di

import org.koin.dsl.module
import tmg.flashback.FlashbackAndroidStartup

actual fun platformModule() = module {
    single { FlashbackAndroidStartup(get()) }
}