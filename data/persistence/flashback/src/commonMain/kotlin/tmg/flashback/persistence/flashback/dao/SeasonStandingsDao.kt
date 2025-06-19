package tmg.flashback.persistence.flashback.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tmg.flashback.persistence.flashback.models.standings.*

@Dao
interface SeasonStandingsDao {

    //region Driver Standings

    @Transaction
    @Query("SELECT * FROM DriverStanding WHERE season == :season")
    fun getDriverStandings(season: Int): Flow<List<DriverStandingWithConstructors>>

    @Transaction
    fun insertDriverStandings(standing: List<DriverStanding>, constructors: List<DriverStandingConstructor>) {
        val seasons = standing
            .map { it.season }
            .distinctBy { it }

        for (x in seasons) {
            deleteDriverStandingDrivers(x)
            deleteDriverStanding(x)
        }
        insertDriverStandingConstructors(constructors)
        insertDriverStandings(standing)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriverStandings(standing: DriverStanding)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriverStandings(standing: List<DriverStanding>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriverStandingConstructors(constructors: List<DriverStandingConstructor>)

    @Query("DELETE FROM DriverStandingConstructor WHERE season == :season")
    fun deleteDriverStandingDrivers(season: Int)

    @Query("DELETE FROM DriverStanding WHERE season == :season")
    fun deleteDriverStanding(season: Int)

    //endregion

    //region Constructor Standings

    @Transaction
    @Query("SELECT * FROM ConstructorStanding WHERE season == :season")
    fun getConstructorStandings(season: Int): Flow<List<ConstructorStandingWithDrivers>>

    @Transaction
    fun insertConstructorStandings(standing: List<ConstructorStanding>, drivers: List<ConstructorStandingDriver>) {
        val seasons = standing
            .map { it.season }
            .distinctBy { it }

        for (x in seasons) {
            deleteConstructorStandingDrivers(x)
            deleteConstructorStanding(x)
        }
        insertConstructorStandingDrivers(drivers)
        insertConstructorStandings(standing)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConstructorStandings(standing: ConstructorStanding)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConstructorStandings(standing: List<ConstructorStanding>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConstructorStandingDrivers(drivers: List<ConstructorStandingDriver>)

    @Query("DELETE FROM ConstructorStandingDriver WHERE season == :season")
    fun deleteConstructorStandingDrivers(season: Int)

    @Query("DELETE FROM ConstructorStanding WHERE season == :season")
    fun deleteConstructorStanding(season: Int)

    //endregion
}