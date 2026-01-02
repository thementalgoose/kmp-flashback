package tmg.flashback.composeApp.di

import org.koin.dsl.module
import tmg.flashback.composeApp.FlashbackAndroidStartup

actual fun platformModule() = module {
    single { FlashbackAndroidStartup(get(), get()) }
}
