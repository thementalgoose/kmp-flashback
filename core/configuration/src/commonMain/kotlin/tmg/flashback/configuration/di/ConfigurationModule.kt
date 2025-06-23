package tmg.flashback.configuration.di

import org.koin.dsl.module
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.manager.ConfigManagerImpl
import tmg.flashback.configuration.repositories.ConfigRepository
import tmg.flashback.configuration.repositories.ConfigRepositoryImpl
import tmg.flashback.configuration.usecases.ApplyConfigUseCase
import tmg.flashback.configuration.usecases.ApplyConfigUseCaseImpl
import tmg.flashback.configuration.usecases.FetchConfigUseCase
import tmg.flashback.configuration.usecases.FetchConfigUseCaseImpl
import tmg.flashback.configuration.usecases.InitialiseConfigUseCase
import tmg.flashback.configuration.usecases.InitialiseConfigUseCaseImpl
import tmg.flashback.configuration.usecases.ResetConfigUseCase
import tmg.flashback.configuration.usecases.ResetConfigUseCaseImpl

val coreConfigurationModule = listOf(platformModule(), module())

internal fun module() = module {
    single<ConfigManager> { ConfigManagerImpl(get()) }

    single<ConfigRepository> { ConfigRepositoryImpl(get()) }

    single<ApplyConfigUseCase> { ApplyConfigUseCaseImpl(get(), get()) }
    single<FetchConfigUseCase> { FetchConfigUseCaseImpl(get(), get()) }
    single<InitialiseConfigUseCase> { InitialiseConfigUseCaseImpl(get()) }
    single<ResetConfigUseCase> { ResetConfigUseCaseImpl(get(), get()) }

}
