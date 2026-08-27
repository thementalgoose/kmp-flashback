package tmg.flashback.feature.glossary.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.glossary.presentation.GlossaryViewModel

val featureGlossaryModule = listOf(module())

internal fun module() = module {
    viewModel { GlossaryViewModel() }
}