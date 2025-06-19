package tmg.flashback.flashbackapi.api.models.circuits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Circuit(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("wikiUrl")
    val wikiUrl: String?,
    @SerialName("location")
    val location: Location?,
    @SerialName("city")
    val city: String,
    @SerialName("country")
    val country: String,
    @SerialName("countryISO")
    val countryISO: String
) {
    companion object
}