package tmg.flashback.persistence.flashback.models.lineup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
)