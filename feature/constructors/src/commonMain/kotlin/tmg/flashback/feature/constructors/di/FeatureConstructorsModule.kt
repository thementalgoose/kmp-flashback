package tmg.flashback.feature.constructors.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.constructors.presentation.stats.ConstructorStatsViewModel

val featureConstructorsModule = listOf(module())

internal fun module() = module {
    viewModel { ConstructorStatsViewModel(get()) }
}