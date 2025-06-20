package tmg.flashback.persistence.flashback.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.ConstructorHistory
import tmg.flashback.persistence.flashback.models.constructors.ConstructorSeason
import tmg.flashback.persistence.flashback.models.constructors.ConstructorSeasonDriver

@Dao
interface ConstructorDao {

    @Query("SELECT * FROM Constructor")
    fun getConstructors(): Flow<List<Constructor>>

    @Query("SELECT * FROM Constructor WHERE id == :id LIMIT 1")
    suspend fun getConstructor(id: String): Constructor?

    @Transaction
    @Query("SELECT * FROM Constructor WHERE id == :id LIMIT 1")
    fun getConstructorHistory(id: String): Flow<ConstructorHistory?>

    @Query("SELECT COUNT(*) FROM ConstructorSeason WHERE constructor_id == :id")
    suspend fun getConstructorSeasonCount(id: String): Int

    @Transaction
    suspend fun insertConstructor(
        constructor: Constructor,
        constructorSeasons: List<ConstructorSeason>,
        constructorSeasonRaces: List<ConstructorSeasonDriver>
    ) {
        insertConstructorSeasonDrivers(constructorSeasonRaces)
        insertConstructorSeasons(constructorSeasons)
        insert(constructor)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(constructor: Constructor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(constructors: List<Constructor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstructorSeasons(constructorSeasons: List<ConstructorSeason>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstructorSeasonDrivers(constructorSeasonDrivers: List<ConstructorSeasonDriver>)

}