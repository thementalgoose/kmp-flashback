package tmg.flashback.persistence.flashback.models.standings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DriverStanding(
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "points")
    val points: Double,
    @ColumnInfo(name = "position")
    val position: Int?,
    @ColumnInfo(name = "in_progress")
    val inProgress: Boolean,
    @ColumnInfo(name = "in_progress_name")
    val inProgressName: String?,
    @ColumnInfo(name = "in_progress_round")
    val inProgressRound: Int?,
    @ColumnInfo(name = "races")
    val races: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${driverId}_${season}",
) {
    companion object
}