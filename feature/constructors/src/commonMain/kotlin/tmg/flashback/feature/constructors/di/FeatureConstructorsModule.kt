package tmg.flashback.feature.constructors.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.constructors.presentation.season.ConstructorSeasonViewModel

val featureConstructorsModule = listOf(module())

internal fun module() = module {
    viewModel { ConstructorSeasonViewModel(get()) }
}