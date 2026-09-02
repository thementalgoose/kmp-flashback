package tmg.flashback.persistence.flashback.models.standings

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

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