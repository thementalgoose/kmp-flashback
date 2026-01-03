package tmg.flashback.composeApp.di

import org.koin.dsl.module
import tmg.flashback.composeApp.FlashbackIOSStartup

actual fun platformModule() = module {
    single { FlashbackIOSStartup() }
}