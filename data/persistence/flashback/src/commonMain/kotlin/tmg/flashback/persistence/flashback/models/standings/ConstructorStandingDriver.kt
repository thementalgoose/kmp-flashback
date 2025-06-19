package tmg.flashback.persistence.flashback.models.standings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConstructorStandingDriver(
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "points")
    val points: Double,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${constructorId}_${season}_${driverId}",
    @ColumnInfo(name = "constructor_season_id")
    val constructorSeasonId: String = "${constructorId}_${season}"
) {
    companion object
}