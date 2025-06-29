package tmg.flashback.feature.drivers.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.drivers.presentation.season.DriverSeasonViewModel

val featureDriversModule = listOf(module())

internal fun module() = module {
    viewModel { DriverSeasonViewModel(get()) }
}