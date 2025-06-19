package tmg.flashback.persistence.flashback

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import tmg.flashback.persistence.flashback.dao.CircuitDao
import tmg.flashback.persistence.flashback.dao.ConstructorDao
import tmg.flashback.persistence.flashback.dao.DriverDao
import tmg.flashback.persistence.flashback.dao.EventsDao
import tmg.flashback.persistence.flashback.dao.OverviewDao
import tmg.flashback.persistence.flashback.dao.ScheduleDao
import tmg.flashback.persistence.flashback.dao.SeasonDao
import tmg.flashback.persistence.flashback.dao.SeasonStandingsDao
import tmg.flashback.persistence.flashback.models.circuit.Circuit
import tmg.flashback.persistence.flashback.models.circuit.CircuitRound
import tmg.flashback.persistence.flashback.models.circuit.CircuitRoundResult
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.ConstructorSeason
import tmg.flashback.persistence.flashback.models.constructors.ConstructorSeasonDriver
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.DriverSeason
import tmg.flashback.persistence.flashback.models.drivers.DriverSeasonRace
import tmg.flashback.persistence.flashback.models.overview.Event
import tmg.flashback.persistence.flashback.models.overview.Overview
import tmg.flashback.persistence.flashback.models.overview.Schedule
import tmg.flashback.persistence.flashback.models.race.QualifyingResult
import tmg.flashback.persistence.flashback.models.race.RaceInfo
import tmg.flashback.persistence.flashback.models.race.RaceResult
import tmg.flashback.persistence.flashback.models.race.SprintQualifyingResult
import tmg.flashback.persistence.flashback.models.race.SprintRaceResult
import tmg.flashback.persistence.flashback.models.standings.ConstructorStanding
import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingDriver
import tmg.flashback.persistence.flashback.models.standings.DriverStanding
import tmg.flashback.persistence.flashback.models.standings.DriverStandingConstructor

internal val DB_NAME = "flashback-database"

expect class FlashbackDatabaseFactory {
    fun createDatabase(): FlashbackDatabase
}

@Database(
    version = 11,
    entities = [
        Circuit::class,
        CircuitRound::class,
        CircuitRoundResult::class,
        Constructor::class,
        ConstructorSeason::class,
        ConstructorSeasonDriver::class,
        Driver::class,
        DriverSeason::class,
        DriverSeasonRace::class,
        Overview::class,
        Schedule::class,
        RaceInfo::class,
        RaceResult::class,
        SprintRaceResult::class,
        SprintQualifyingResult::class,
        QualifyingResult::class,
        DriverStanding::class,
        DriverStandingConstructor::class,
        ConstructorStanding::class,
        ConstructorStandingDriver::class,
        Event::class
    ],
    exportSchema = false
)
abstract class FlashbackDatabase: RoomDatabase() {
    abstract fun overviewDao(): OverviewDao
    abstract fun circuitDao(): CircuitDao
    abstract fun constructorDao(): ConstructorDao
    abstract fun driverDao(): DriverDao
    abstract fun seasonDao(): SeasonDao
    abstract fun seasonStandingDao(): SeasonStandingsDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun eventsDao(): EventsDao
}

// The Room compiler generates the `actual` implementations.
//@Suppress("KotlinNoActualForExpect")
//expect object FlashbackDatabaseConstructor : RoomDatabaseConstructor<FlashbackDatabase> {
//    override fun initialize(): FlashbackDatabase
//}