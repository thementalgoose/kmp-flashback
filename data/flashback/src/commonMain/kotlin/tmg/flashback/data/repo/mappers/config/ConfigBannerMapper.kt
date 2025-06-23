package tmg.flashback.data.repo.mappers.config

import tmg.flashback.data.repo.model.Banner
import tmg.flashback.data.repo.model.config.BannerItemJson
import tmg.flashback.data.repo.model.config.BannerJson

internal fun BannerJson.toModel(): List<Banner> {
    return this.banners?.mapNotNull { it.toModel() } ?: emptyList()
}

private fun BannerItemJson.toModel() = try {
    Banner(
        message = this.msg!!,
        url = this.url,
        highlight = this.highlight == true,
        season = this.season
    )
} catch (ignored: NullPointerException) {
    null
}