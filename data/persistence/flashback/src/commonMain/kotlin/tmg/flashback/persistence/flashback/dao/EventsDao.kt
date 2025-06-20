package tmg.flashback.persistence.flashback.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tmg.flashback.persistence.flashback.models.overview.Event

@Dao
interface EventsDao {

    //region Winter Testing

    @Query("SELECT * FROM Event WHERE season == :season")
    fun getEvents(season: Int): Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(winterTesting: List<Event>)

    @Query("DELETE FROM Event WHERE season == :season")
    suspend fun deleteEventsForSeason(season: Int)

    @Transaction
    suspend fun replaceEventsForSeason(season: Int, testing: List<Event>) {
        deleteEventsForSeason(season)
        insertEvents(testing)
    }

    //endregion
}