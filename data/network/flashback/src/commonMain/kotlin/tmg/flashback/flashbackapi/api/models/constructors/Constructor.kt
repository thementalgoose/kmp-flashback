package tmg.flashback.flashbackapi.api.models.constructors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Constructor(
    @SerialName("id")
    val id: String,
    @SerialName("colour")
    val colour: String,
    @SerialName("name")
    val name: String,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("nationality")
    val nationality: String,
    @SerialName("nationalityISO")
    val nationalityISO: String,
    @SerialName("wikiUrl")
    val wikiUrl: String?
) {
    companion object
}