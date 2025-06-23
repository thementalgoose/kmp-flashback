package tmg.flashback.data.repo.model.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BannerJson(
    @SerialName("banners")
    val banners: List<BannerItemJson>?
)

@Serializable
internal data class BannerItemJson(
    @SerialName("msg")
    val msg: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("highlight")
    val highlight: Boolean?,
    @SerialName("season")
    val season: Int?
)