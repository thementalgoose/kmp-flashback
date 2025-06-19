package tmg.flashback.persistence.flashback.models.constructors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "colour")
    val colour: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "photoUrl")
    val photoUrl: String?,
    @ColumnInfo(name = "nationality")
    val nationality: String,
    @ColumnInfo(name = "nationality_iso")
    val nationalityISO: String,
    @ColumnInfo(name = "wiki_url")
    val wikiUrl: String?
) {
    companion object
}