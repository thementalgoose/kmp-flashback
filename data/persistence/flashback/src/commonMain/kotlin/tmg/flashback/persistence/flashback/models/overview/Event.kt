package tmg.flashback.persistence.flashback.models.overview

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

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