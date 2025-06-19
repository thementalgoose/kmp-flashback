package tmg.flashback.persistence.flashback.models.constructors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConstructorSeasonDriver(
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "points")
    val points: Double,
    @ColumnInfo(name = "championship_position")
    val championshipPosition: Int?,
    @ColumnInfo(name = "wins")
    val wins: Int,
    @ColumnInfo(name = "races")
    val races: Int,
    @ColumnInfo(name = "podiums")
    val podiums: Int,
    @ColumnInfo(name = "points_finishes")
    val pointsFinishes: Int,
    @ColumnInfo(name = "pole")
    val pole: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${constructorId}_${season}_${driverId}",
    @ColumnInfo(name = "constructor_season_id")
    val constructorSeasonId: String = "${constructorId}_${season}"
) {
    companion object
}