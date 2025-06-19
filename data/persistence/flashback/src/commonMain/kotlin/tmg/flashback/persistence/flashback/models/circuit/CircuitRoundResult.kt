package tmg.flashback.persistence.flashback.models.circuit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CircuitRoundResult(
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "round")
    val round: Int,
    @ColumnInfo(name = "position")
    val position: Int,
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${season}_${round}_${position}",
    @ColumnInfo(name = "season_round_id")
    val seasonRound: String = "${season}_${round}",
) {
    companion object
}