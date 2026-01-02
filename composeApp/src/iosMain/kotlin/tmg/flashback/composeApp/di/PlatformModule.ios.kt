package tmg.flashback.composeApp.di

import org.koin.dsl.module
import tmg.flashback.FlashbackIOSStartup

actual fun platformModule() = module {
    single { FlashbackIOSStartup() }
}