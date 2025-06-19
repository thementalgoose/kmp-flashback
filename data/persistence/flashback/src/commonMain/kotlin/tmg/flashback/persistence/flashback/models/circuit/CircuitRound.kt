package tmg.flashback.persistence.flashback.models.circuit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CircuitRound(
    @ColumnInfo(name = "circuit_id")
    val circuitId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "round")
    val round: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "wikiUrl")
    val wikiUrl: String?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${circuitId}_${season}_${round}",
    @ColumnInfo(name = "season_round")
    val seasonRound: String = "${season}_${round}"
) {
    companion object
}