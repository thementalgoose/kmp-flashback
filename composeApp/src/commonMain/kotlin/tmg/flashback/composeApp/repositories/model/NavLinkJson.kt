package tmg.flashback.composeApp.repositories.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NavLinksJson(
    @SerialName("extraLinks")
    val nav: List<NavLinkJson>
)

@Serializable
internal data class NavLinkJson(
    @SerialName("type")
    val type: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String? = null,
)
