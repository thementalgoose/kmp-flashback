package tmg.flashback.eastereggs.di

import org.koin.dsl.module
import tmg.flashback.eastereggs.repository.EasterEggsRepository
import tmg.flashback.eastereggs.repository.EasterEggsRepositoryImpl
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCaseImpl
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCaseImpl
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCaseImpl
import tmg.flashback.eastereggs.usecases.IsUkraineEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsUkraineEnabledUseCaseImpl

val easterEggsModule = listOf(module())

internal fun module() = module {
    single<EasterEggsRepository> { EasterEggsRepositoryImpl(get()) }

    single<IsMenuIconEnabledUseCase> { IsMenuIconEnabledUseCaseImpl(get()) }
    single<IsSnowEnabledUseCase> { IsSnowEnabledUseCaseImpl(get(), get()) }
    single<IsSummerEnabledUseCase> { IsSummerEnabledUseCaseImpl(get()) }
    single<IsUkraineEnabledUseCase> { IsUkraineEnabledUseCaseImpl(get()) }
}