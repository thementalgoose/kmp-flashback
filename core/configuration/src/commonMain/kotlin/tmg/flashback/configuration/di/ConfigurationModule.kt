package tmg.flashback.configuration.di

import org.koin.dsl.module
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.manager.ConfigManagerImpl

val configurationModules = listOf(platformModule(), module())

internal fun module() = module {
    single<ConfigManager> { ConfigManagerImpl(get()) }
}
