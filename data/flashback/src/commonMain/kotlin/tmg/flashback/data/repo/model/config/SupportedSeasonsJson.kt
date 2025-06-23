package tmg.flashback.data.repo.model.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SupportedSeasonsJson(
    @SerialName("seasons")
    val seasons: List<SupportedSeasonJson>? = null
)

@Serializable
internal data class SupportedSeasonJson(
    @SerialName("s")
    val s: Int?
)