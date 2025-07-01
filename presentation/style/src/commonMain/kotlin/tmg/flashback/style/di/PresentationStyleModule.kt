package tmg.flashback.style.di

import org.koin.dsl.module
import tmg.flashback.style.theme.ThemeManager
import tmg.flashback.style.theme.ThemeManagerImpl

val presentationStyleModule = listOf(module())

internal fun module() = module {
    single<ThemeManager> { ThemeManagerImpl(get()) }
}