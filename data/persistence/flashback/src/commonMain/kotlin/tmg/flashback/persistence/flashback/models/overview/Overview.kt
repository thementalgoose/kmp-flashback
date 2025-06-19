package tmg.flashback.persistence.flashback.models.overview

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Overview(
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "round")
    val round: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "circuit_id")
    val circuitId: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "laps")
    val laps: String?,
    @ColumnInfo(name = "has_race_data")
    val hasRace: Boolean,
    @ColumnInfo(name = "has_sprint_data")
    val hasSprint: Boolean,
    @ColumnInfo(name = "has_qualifying_data")
    val hasQualifying: Boolean,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${season}_${round}"
) {
    companion object
}