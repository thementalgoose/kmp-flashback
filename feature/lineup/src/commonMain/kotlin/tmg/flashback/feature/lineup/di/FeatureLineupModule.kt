package tmg.flashback.feature.lineup.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.lineup.presentation.LineupViewModel

val featureLineupModule = listOf(module())

internal fun module() = module {
    viewModel { LineupViewModel(get() )}
}