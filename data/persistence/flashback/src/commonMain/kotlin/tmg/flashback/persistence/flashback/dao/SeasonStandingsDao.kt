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
    suspend fun insertDriverStandings(standing: List<DriverStanding>, constructors: List<DriverStandingConstructor>) {
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
    suspend fun insertDriverStandings(standing: DriverStanding)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriverStandings(standing: List<DriverStanding>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriverStandingConstructors(constructors: List<DriverStandingConstructor>)

    @Query("DELETE FROM DriverStandingConstructor WHERE season == :season")
    suspend fun deleteDriverStandingDrivers(season: Int)

    @Query("DELETE FROM DriverStanding WHERE season == :season")
    suspend fun deleteDriverStanding(season: Int)

    //endregion

    //region Constructor Standings

    @Transaction
    @Query("SELECT * FROM ConstructorStanding WHERE season == :season")
    fun getConstructorStandings(season: Int): Flow<List<ConstructorStandingWithDrivers>>

    @Transaction
    suspend fun insertConstructorStandings(
        standing: List<ConstructorStanding>,
        drivers: List<ConstructorStandingDriver>
    ) {
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
    suspend fun insertConstructorStandings(standing: ConstructorStanding)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstructorStandings(standing: List<ConstructorStanding>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertConstructorStandingDrivers(drivers: List<ConstructorStandingDriver>)

    @Query("DELETE FROM ConstructorStandingDriver WHERE season == :season")
    suspend fun deleteConstructorStandingDrivers(season: Int)

    @Query("DELETE FROM ConstructorStanding WHERE season == :season")
    suspend fun deleteConstructorStanding(season: Int)

    //endregion
}