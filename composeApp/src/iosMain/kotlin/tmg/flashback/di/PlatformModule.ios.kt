package tmg.flashback.di

import org.koin.dsl.module
import tmg.flashback.FlashbackIOSStartup

actual fun platformModule() = module {
    single { FlashbackIOSStartup(get()) }
}