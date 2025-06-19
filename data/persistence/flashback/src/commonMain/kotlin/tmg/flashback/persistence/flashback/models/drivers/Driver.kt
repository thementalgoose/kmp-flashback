package tmg.flashback.persistence.flashback.models.drivers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Driver(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "dob")
    val dob: String,
    @ColumnInfo(name = "nationality")
    val nationality: String,
    @ColumnInfo(name = "nationality_iso")
    val nationalityISO: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String?,
    @ColumnInfo(name = "wiki_url")
    val wikiUrl: String?,
    @ColumnInfo(name = "driver_number")
    val number: Int?,
    @ColumnInfo(name = "driver_code")
    val code: String?
) {
    companion object
}