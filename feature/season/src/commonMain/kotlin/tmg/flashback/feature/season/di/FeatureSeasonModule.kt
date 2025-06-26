package tmg.flashback.feature.season.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.season.presentation.calendar.CalendarScreenViewModel
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsViewModel
import tmg.flashback.feature.season.presentation.shared.providedby.ProvidedByViewModel
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolderImpl
import tmg.flashback.feature.season.presentation.shared.seasonpicker.SeasonPickerViewModel
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsViewModel
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.feature.season.repositories.CalendarRepositoryImpl
import tmg.flashback.feature.season.usecases.DefaultSeasonUseCase
import tmg.flashback.feature.season.usecases.DefaultSeasonUseCaseImpl

val featureSeasonModule = listOf(module())

internal fun module() = module {
    viewModel { ProvidedByViewModel(get()) }
    viewModel { DriverStandingsViewModel(get(), get(), get(), get()) }
    viewModel { TeamStandingsViewModel(get(), get(), get(), get()) }
    viewModel { CalendarScreenViewModel(get(), get(), get(), get(), get()) }
    viewModel { SeasonPickerViewModel(get()) }
    single<CurrentSeasonHolder> { CurrentSeasonHolderImpl(get(), get(), get()) }

    single<CalendarRepository> { CalendarRepositoryImpl(get()) }

    single<DefaultSeasonUseCase> { DefaultSeasonUseCaseImpl(get(), get()) }
}