package tmg.flashback.feature.circuits.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.circuits.presentation.all.AllCircuitsViewModel
import tmg.flashback.feature.circuits.presentation.circuit.CircuitViewModel

val featureCircuitsModule = listOf(module())

internal fun module() = module {
    viewModel { AllCircuitsViewModel(get()) }
    viewModel { CircuitViewModel(get())  }
}