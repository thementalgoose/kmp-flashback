package tmg.flashback.feature.paddock.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.paddock.presentation.PaddockViewModel

val featurePaddockModule = listOf(module())

internal fun module() = module {
    viewModel { PaddockViewModel() }
}