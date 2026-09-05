package tmg.flashback.feature.weekend.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.weekend.presentation.WeekendViewModel
import tmg.flashback.feature.weekend.presentation.data.info.InfoDataMapper
import tmg.flashback.feature.weekend.presentation.data.info.InfoDataMapperImpl
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingDataMapperImpl
import tmg.flashback.feature.weekend.presentation.data.race.RaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.race.RaceDataMapperImpl
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingDataMapperImpl
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceDataMapperImpl
import tmg.flashback.feature.weekend.repositories.WeatherRepository
import tmg.flashback.feature.weekend.repositories.WeatherRepositoryImpl
import tmg.flashback.feature.weekend.repositories.WeekendRepository
import tmg.flashback.feature.weekend.repositories.WeekendRepositoryImpl
import tmg.flashback.feature.weekend.usecases.GetPreviousRaceUseCase
import tmg.flashback.feature.weekend.usecases.GetPreviousRaceUseCaseImpl

val featureWeekendModule = listOf(module())

internal fun module() = module {
    viewModel { WeekendViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<WeekendRepository> { WeekendRepositoryImpl(get()) }
    single<GetPreviousRaceUseCase> { GetPreviousRaceUseCaseImpl(get()) }

    single<InfoDataMapper> { InfoDataMapperImpl(get(), get()) }
    single<QualifyingDataMapper> { QualifyingDataMapperImpl() }
    single<RaceDataMapper> { RaceDataMapperImpl() }
    single<SprintQualifyingDataMapper> { SprintQualifyingDataMapperImpl() }
    single<SprintRaceDataMapper> { SprintRaceDataMapperImpl() }

}