package tmg.flashback.data.repo.di

import org.koin.dsl.module
import tmg.flashback.data.repo.mappers.app.CircuitMapper
import tmg.flashback.data.repo.mappers.app.ConstructorDataMapper
import tmg.flashback.data.repo.mappers.app.ConstructorMapper
import tmg.flashback.data.repo.mappers.app.ConstructorStandingMapper
import tmg.flashback.data.repo.mappers.app.DriverDataMapper
import tmg.flashback.data.repo.mappers.app.DriverMapper
import tmg.flashback.data.repo.mappers.app.DriverStandingMapper
import tmg.flashback.data.repo.mappers.app.EventMapper
import tmg.flashback.data.repo.mappers.app.OverviewMapper
import tmg.flashback.data.repo.mappers.app.RaceMapper
import tmg.flashback.data.repo.mappers.app.ScheduleMapper
import tmg.flashback.data.repo.mappers.app.SeasonMapper
import tmg.flashback.data.repo.mappers.network.NetworkCircuitDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkCircuitMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorStandingMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverStandingMapper
import tmg.flashback.data.repo.mappers.network.NetworkEventMapper
import tmg.flashback.data.repo.mappers.network.NetworkOverviewMapper
import tmg.flashback.data.repo.mappers.network.NetworkRaceDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkRaceMapper
import tmg.flashback.data.repo.mappers.network.NetworkScheduleMapper
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.data.repo.repository.CircuitRepositoryImpl
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.data.repo.repository.ConstructorRepositoryImpl
import tmg.flashback.data.repo.repository.DriverRepository
import tmg.flashback.data.repo.repository.DriverRepositoryImpl
import tmg.flashback.data.repo.repository.EventRepository
import tmg.flashback.data.repo.repository.EventRepositoryImpl
import tmg.flashback.data.repo.repository.InfoRepository
import tmg.flashback.data.repo.repository.InfoRepositoryImpl
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.OverviewRepositoryImpl
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.RaceRepositoryImpl
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.data.repo.repository.SeasonRepositoryImpl

val dataFlashbackModule = listOf(module())

internal fun module() = module {
    single { CircuitMapper(get(), get()) }
    single { ConstructorDataMapper() }
    single { ConstructorMapper(get(), get()) }
    single { ConstructorStandingMapper(get(), get()) }
    single { DriverDataMapper() }
    single { DriverMapper(get(), get(), get()) }
    single { DriverStandingMapper(get(), get()) }
    single { EventMapper() }
    single { OverviewMapper(get()) }
    single { RaceMapper(get(), get(), get(), get()) }
    single { ScheduleMapper() }
    single { SeasonMapper(get(), get()) }

    single { NetworkCircuitDataMapper() }
    single { NetworkCircuitMapper() }
    single { NetworkConstructorDataMapper() }
    single { NetworkConstructorMapper() }
    single { NetworkConstructorStandingMapper() }
    single { NetworkDriverDataMapper() }
    single { NetworkDriverMapper() }
    single { NetworkDriverStandingMapper() }
    single { NetworkEventMapper() }
    single { NetworkOverviewMapper() }
    single { NetworkRaceDataMapper() }
    single { NetworkRaceMapper() }
    single { NetworkScheduleMapper() }

    single<RaceRepository> { RaceRepositoryImpl(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    single<OverviewRepository> { OverviewRepositoryImpl(get(), get(), get(), get(), get(), get()) }
    single<SeasonRepository> { SeasonRepositoryImpl(get(), get(), get(), get()) }
    single<EventRepository> { EventRepositoryImpl(get(), get(), get(), get()) }
    single<DriverRepository> { DriverRepositoryImpl(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    single<ConstructorRepository> { ConstructorRepositoryImpl(get(), get(), get(), get(), get(), get(), get()) }
    single<CircuitRepository> { CircuitRepositoryImpl(get(), get(), get(), get(), get(), get(), get()) }

    single<InfoRepository> { InfoRepositoryImpl(get()) }
}