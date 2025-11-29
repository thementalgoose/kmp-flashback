package tmg.flashback.feature.drivers.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.drivers.presentation.comparison.DriverComparisonViewModel
import tmg.flashback.feature.drivers.presentation.stats.DriverStatsViewModel

val featureDriversModule = listOf(module())

internal fun module() = module {
    viewModel { DriverStatsViewModel(get()) }
    viewModel { DriverComparisonViewModel(get(), get(), get()) }
}