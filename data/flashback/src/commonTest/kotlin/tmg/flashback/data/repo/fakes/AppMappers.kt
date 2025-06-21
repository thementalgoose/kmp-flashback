package tmg.flashback.data.repo.fakes

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

fun fakeDriverDataMapper() = DriverDataMapper()

fun fakeConstructorDataMapper() = ConstructorDataMapper()

fun fakeScheduleMapper() = ScheduleMapper()

fun fakeEventMapper() = EventMapper()

fun fakeCircuitMapper() = CircuitMapper(
    driverMapper = fakeDriverDataMapper(),
    constructorMapper = fakeConstructorDataMapper()
)

fun fakeRaceMapper() = RaceMapper(
    circuitMapper = fakeCircuitMapper(),
    driverDataMapper = fakeDriverDataMapper(),
    constructorDataMapper = fakeConstructorDataMapper(),
    scheduleMapper = fakeScheduleMapper()
)

fun fakeConstructorStandingMapper() = ConstructorStandingMapper(
    driverDataMapper = fakeDriverDataMapper(),
    constructorDataMapper = fakeConstructorDataMapper()
)

fun fakeDriverMapper() = DriverMapper(
    driverDataMapper = fakeDriverDataMapper(),
    constructorDataMapper = fakeConstructorDataMapper(),
    raceDataMapper = fakeRaceMapper()
)

fun fakeConstructorMapper() = ConstructorMapper(
    driverDataMapper = fakeDriverDataMapper(),
    constructorDataMapper = fakeConstructorDataMapper(),
)

fun fakeDriverStandingMapper() = DriverStandingMapper(
    driverDataMapper = fakeDriverDataMapper(),
    constructorDataMapper = fakeConstructorDataMapper()
)

fun fakeOverviewMapper() = OverviewMapper(
    scheduleMapper = fakeScheduleMapper()
)

fun fakeSeasonMapper() = SeasonMapper(
    raceMapper = fakeRaceMapper(),
    eventMapper = fakeEventMapper()
)