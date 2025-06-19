package tmg.flashback.persistence.flashback.models.race

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SprintQualifyingResult(
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "round")
    val round: Int,
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "qualified")
    val qualified: Int,
    @ColumnInfo(name = "sq1")
    val sq1: String?,
    @ColumnInfo(name = "sq2")
    val sq2: String?,
    @ColumnInfo(name = "sq3")
    val sq3: String?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${driverId}_${season}_${round}",
    @ColumnInfo(name = "season_round_id")
    val seasonRoundId: String = "${season}_${round}"
) {
    companion object
}