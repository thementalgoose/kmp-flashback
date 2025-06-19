package tmg.flashback.persistence.flashback.dao

import androidx.room.*
import java.time.LocalDate
import tmg.flashback.persistence.flashback.models.overview.OverviewWithCircuit
import tmg.flashback.persistence.flashback.models.overview.Schedule
import tmg.utilities.extensions.format

@Dao
interface ScheduleDao {

    @Transaction
    @Query("SELECT * FROM Overview WHERE date >= :fromDate")
    suspend fun getUpcomingEvents(fromDate: String): List<OverviewWithCircuit>

    @Transaction
    suspend fun getUpcomingEvents(fromDate: LocalDate): List<OverviewWithCircuit> {
        val fromDateString = fromDate.format("yyyy-MM-dd") ?: return emptyList()
        return getUpcomingEvents(fromDateString)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schedule: Schedule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schedule: List<Schedule>)

    @Transaction
    fun replaceAllForRace(season: Int, round: Int, schedule: List<Schedule>) {
        deleteForRace(season, round)
        insertAll(schedule)
    }

    @Transaction
    fun replaceAllForSeason(season: Int, schedule: List<Schedule>) {
        deleteForSeason(season)
        insertAll(schedule)
    }

    @Query("DELETE FROM Schedule WHERE season == :season AND round == :round")
    fun deleteForRace(season: Int, round: Int)

    @Query("DELETE FROM Schedule WHERE season == :season")
    fun deleteForSeason(season: Int)
}