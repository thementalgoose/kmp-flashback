package tmg.flashback.flashbackapi.api.models.drivers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    @SerialName("id")
    val id: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("dob")
    val dob: String,
    @SerialName("nationality")
    val nationality: String,
    @SerialName("nationalityISO")
    val nationalityISO: String,
    @SerialName("photoUrl")
    val photoUrl: String? = null,
    @SerialName("wikiUrl")
    val wikiUrl: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("permanentNumber")
    val permanentNumber: String? = null
) {
    companion object
}