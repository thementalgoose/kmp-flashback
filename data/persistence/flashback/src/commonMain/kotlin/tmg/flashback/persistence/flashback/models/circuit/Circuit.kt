package tmg.flashback.persistence.flashback.models.circuit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Circuit(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "wiki_url")
    val wikiUrl: String?,
    @ColumnInfo(name = "location_lat")
    val locationLat: Double?,
    @ColumnInfo(name = "location_lng")
    val locationLng: Double?,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "country_iso")
    val countryISO: String
) {
    companion object
}