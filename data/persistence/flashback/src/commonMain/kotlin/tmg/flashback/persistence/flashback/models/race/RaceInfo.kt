package tmg.flashback.persistence.flashback.models.race

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class RaceInfo(
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "round")
    val round: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "circuit_id")
    val circuitId: String,
    @ColumnInfo(name = "laps")
    val laps: String?,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "wikiUrl")
    val wikiUrl: String?,
    @ColumnInfo(name = "aerialUrl")
    val aerialUrl: String?,
    @ColumnInfo(name = "youtube")
    val youtube: String?,
    @ColumnInfo(name = "cancelled")
    val cancelled: Boolean,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${season}_${round}"
) {
    companion object
}