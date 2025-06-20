package tmg.flashback.persistence.flashback.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.DriverHistory
import tmg.flashback.persistence.flashback.models.drivers.DriverSeason
import tmg.flashback.persistence.flashback.models.drivers.DriverSeasonRace

@Dao
interface DriverDao {

    @Query("SELECT * FROM driver")
    fun getDrivers(): Flow<List<Driver>>

    @Query("SELECT * FROM driver WHERE id == :id LIMIT 1")
    fun getDriver(id: String): Flow<Driver?>

    @Transaction
    @Query("SELECT * FROM driver WHERE id == :id LIMIT 1")
    fun getDriverHistory(id: String): Flow<DriverHistory?>

    @Query("SELECT COUNT(*) FROM DriverSeason WHERE driver_id == :id")
    suspend fun getDriverSeasonCount(id: String): Int

    @Transaction
    suspend fun insertDriver(
        driver: Driver,
        driverSeasons: List<DriverSeason>,
        driverSeasonRaces: List<DriverSeasonRace>
    ) {
        insertDriverSeasonRaces(driverSeasonRaces)
        insertDriverSeasons(driverSeasons)
        insert(driver)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(driver: Driver)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<Driver>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriverSeasons(driverSeasons: List<DriverSeason>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriverSeasonRaces(driverSeasonRaces: List<DriverSeasonRace>)
}