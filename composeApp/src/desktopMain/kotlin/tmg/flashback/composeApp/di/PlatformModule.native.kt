package tmg.flashback.composeApp.di

import org.koin.dsl.module
import tmg.flashback.composeApp.FlashbackDesktopStartup

actual fun platformModule() = module {
    single { FlashbackDesktopStartup() }
}