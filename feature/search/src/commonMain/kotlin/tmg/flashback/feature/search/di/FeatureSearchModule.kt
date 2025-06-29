package tmg.flashback.feature.search.di

import org.koin.dsl.module
import tmg.flashback.feature.search.usecases.IsSearchEnabledUseCase
import tmg.flashback.feature.search.usecases.IsSearchEnabledUseCseImpl

val featureSearchModule = listOf(module())

internal fun module() = module {
    single<IsSearchEnabledUseCase> { IsSearchEnabledUseCseImpl() }
}