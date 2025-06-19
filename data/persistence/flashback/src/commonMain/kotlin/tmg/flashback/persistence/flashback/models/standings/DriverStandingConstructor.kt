package tmg.flashback.persistence.flashback.models.standings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DriverStandingConstructor(
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "points")
    val points: Double,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${driverId}_${season}_${constructorId}",
    @ColumnInfo(name = "driver_season_id")
    val driverSeasonId: String = "${driverId}_${season}"
) {
    companion object
}