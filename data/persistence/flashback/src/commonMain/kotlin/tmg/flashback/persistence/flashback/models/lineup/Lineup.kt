package tmg.flashback.persistence.flashback.models.lineup

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class Lineup(
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${season}_${driverId}",
) {
    companion object
}