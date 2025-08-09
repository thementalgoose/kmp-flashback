package tmg.flashback.feature.highlights.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.highlights.domain.GetNewsItemsUseCase
import tmg.flashback.feature.highlights.domain.GetNewsItemsUseCaseImpl
import tmg.flashback.feature.highlights.presentation.HighlightsViewModel
import tmg.flashback.feature.highlights.repositories.HighlightsRepository
import tmg.flashback.feature.highlights.repositories.HighlightsRepositoryImpl

val featureHighlightsModule = listOf(module())

internal fun module() = module {

    single<HighlightsRepository> { HighlightsRepositoryImpl(get()) }
    single<GetNewsItemsUseCase> { GetNewsItemsUseCaseImpl(get()) }

    viewModel { HighlightsViewModel(get(), get(), get()) }
}