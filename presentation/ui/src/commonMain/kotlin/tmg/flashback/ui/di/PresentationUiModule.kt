package tmg.flashback.ui.di

import org.koin.dsl.module
import tmg.flashback.ui.theme.ThemeManager
import tmg.flashback.ui.theme.ThemeManagerImpl

val presentationUiModule = listOf(module())

internal fun module() = module {
    single<ThemeManager> { ThemeManagerImpl(get()) }
}