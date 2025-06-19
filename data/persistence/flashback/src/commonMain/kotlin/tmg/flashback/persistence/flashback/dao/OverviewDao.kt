package tmg.flashback.persistence.flashback.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tmg.flashback.persistence.flashback.models.overview.Overview
import tmg.flashback.persistence.flashback.models.overview.OverviewWithCircuit

@Dao
interface OverviewDao {

    @Transaction
    @Query("SELECT * FROM overview WHERE season == :season AND round == :round LIMIT 1")
    fun getOverview(season: Int, round: Int): Flow<OverviewWithCircuit?>

    @Transaction
    @Query("SELECT * FROM overview WHERE season = :season")
    fun getOverview(season: Int): Flow<List<OverviewWithCircuit>>

    @Transaction
    @Query("SELECT * FROM overview")
    fun getOverview(): Flow<List<OverviewWithCircuit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(overviews: List<Overview>)

    @Transaction
    @Query("SELECT * FROM overview WHERE season == :season ORDER BY round DESC LIMIT 1")
    suspend fun getLastRound(season: Int): OverviewWithCircuit?

    @Query("DELETE FROM overview WHERE season == :season AND round == :round")
    fun deleteOverview(season: Int, round: Int)
}