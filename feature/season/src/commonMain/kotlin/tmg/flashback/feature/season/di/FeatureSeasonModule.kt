package tmg.flashback.feature.season.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.season.presentation.calendar.CalendarViewModel

val featureSeasonModule = listOf(module())

internal fun module() = module {
    viewModel { CalendarViewModel() }
}