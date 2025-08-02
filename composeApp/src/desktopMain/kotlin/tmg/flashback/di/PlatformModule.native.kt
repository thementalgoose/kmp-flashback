package tmg.flashback.di

import org.koin.dsl.module
import tmg.flashback.FlashbackDesktopStartup

actual fun platformModule() = module {
    single { FlashbackDesktopStartup() }
}