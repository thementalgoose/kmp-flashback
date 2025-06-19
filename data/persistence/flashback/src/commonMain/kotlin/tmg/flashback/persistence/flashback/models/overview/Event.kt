package tmg.flashback.persistence.flashback.models.overview

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "label")
    val label: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "type")
    val type: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "$season-$type-$date"
) {
    companion object
}