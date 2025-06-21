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
}