package tmg.flashback.webbrowser.di

import org.koin.dsl.module
import tmg.flashback.webbrowser.manager.WebManager
import tmg.flashback.webbrowser.manager.WebManagerImpl

actual fun platformModule() = module {
    single<WebManager> { WebManagerImpl() }
}