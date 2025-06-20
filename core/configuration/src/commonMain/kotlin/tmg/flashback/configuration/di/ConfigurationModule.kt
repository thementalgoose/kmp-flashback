package tmg.flashback.configuration.di

import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.manager.ConfigManagerImpl

val configurationModule = module {
    single<ConfigManager> { ConfigManagerImpl() }
}