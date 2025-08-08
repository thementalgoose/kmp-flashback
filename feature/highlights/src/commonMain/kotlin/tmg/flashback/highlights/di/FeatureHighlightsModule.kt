package tmg.flashback.highlights.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.highlights.presentation.HighlightsViewModel
import tmg.flashback.highlights.repositories.HighlightsRepository
import tmg.flashback.highlights.repositories.HighlightsRepositoryImpl

val featureHighlightsModule = listOf(module())

internal fun module() = module {

    single<HighlightsRepository> { HighlightsRepositoryImpl(get()) }

    viewModel { HighlightsViewModel(get()) }
}