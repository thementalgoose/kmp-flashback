package tmg.flashback.feature.season.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.season.presentation.calendar.CalendarScreenViewModel
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.feature.season.repositories.CalendarRepositoryImpl

val featureSeasonModule = listOf(module())

internal fun module() = module {
    viewModel { CalendarScreenViewModel(get()) }

    single<CalendarRepository> { CalendarRepositoryImpl(get()) }
}