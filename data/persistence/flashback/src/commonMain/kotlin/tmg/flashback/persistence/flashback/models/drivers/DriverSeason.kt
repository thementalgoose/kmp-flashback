package tmg.flashback.persistence.flashback.models.drivers

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class DriverSeason(
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "championship_standing")
    val championshipStanding: Int?,
    @ColumnInfo(name = "points")
    val points: Double,
    @ColumnInfo(name = "in_progress")
    val inProgress: Boolean,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${driverId}_${season}"
) {
    companion object
}