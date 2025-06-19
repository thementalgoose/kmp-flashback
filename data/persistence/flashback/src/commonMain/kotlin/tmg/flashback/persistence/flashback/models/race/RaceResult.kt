package tmg.flashback.persistence.flashback.models.race

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RaceResult(
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "round")
    val round: Int,
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "points")
    val points: Double,
    @ColumnInfo(name = "qualified")
    val qualified: Int?,
    @ColumnInfo(name = "grid_position")
    val gridPos: Int?,
    @ColumnInfo(name = "finished")
    val finished: Int,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "time")
    val time: String?,
    @Embedded(prefix = "fastest_lap_")
    val fastestLap: FastestLap?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${driverId}_${season}_${round}",
    @ColumnInfo(name = "season_round_id")
    val seasonRoundId: String = "${season}_${round}"
) {
    companion object
}