package tmg.flashback.feature.season.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.season.presentation.calendar.CalendarScreenViewModel
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsViewModel
import tmg.flashback.feature.season.presentation.shared.device_time.DeviceTimePrompt
import tmg.flashback.feature.season.presentation.shared.device_time.DeviceTimeViewModel
import tmg.flashback.feature.season.presentation.shared.providedby.ProvidedByViewModel
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolderImpl
import tmg.flashback.feature.season.presentation.shared.seasonpicker.SeasonPickerViewModel
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsViewModel
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.feature.season.repositories.CalendarRepositoryImpl
import tmg.flashback.feature.season.usecases.DefaultSeasonUseCase
import tmg.flashback.feature.season.usecases.DefaultSeasonUseCaseImpl

@Module
@ComponentScan("tmg.flashback.feature.season")
class FeatureSeasonModule