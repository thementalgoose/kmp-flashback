package tmg.flashback.persistence.flashback.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow
import tmg.flashback.persistence.flashback.models.lineup.Lineup
import tmg.flashback.persistence.flashback.models.lineup.LineupWithDrivers

@Dao
interface LineupDao {

    @Transaction
    @Query("SELECT * FROM Lineup")
    fun getLineup(): Flow<List<LineupWithDrivers>>

    @Transaction
    @Query("SELECT * FROM Lineup WHERE season == :season")
    fun getLineup(season: Int): Flow<List<LineupWithDrivers>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLineup(lineup: List<Lineup>)

    @Query("DELETE FROM Lineup WHERE season == :season")
    suspend fun deleteLineups(season: Int)

    @Query("DELETE FROM Lineup")
    suspend fun deleteLineups()

    @Transaction
    suspend fun replaceLineups(lineup: List<Lineup>) {
        deleteLineups()
        insertLineup(lineup)
    }
}