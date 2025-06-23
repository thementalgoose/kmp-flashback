package tmg.flashback.data.repo.repository

import kotlinx.datetime.LocalDate
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.data.repo.mappers.config.convert
import tmg.flashback.data.repo.mappers.config.toModel
import tmg.flashback.data.repo.model.Banner
import tmg.flashback.data.repo.model.config.BannerJson
import tmg.flashback.data.repo.model.config.SupportedSeasonsJson
import tmg.flashback.infrastructure.datetime.now

interface InfoRepository {
    val defaultSeason: Int
    val dataProvidedBy: String?
    val supportedSeasons: Set<Int>
    val infoBanners: List<Banner>
}

internal class InfoRepositoryImpl(
    private val configManager: ConfigManager
): InfoRepository {

    /**
     * Banner to be displayed at the top of the screen
     */
    override val infoBanners: List<Banner>
        get() = configManager
            .getJson<BannerJson>(keyDefaultBanners, BannerJson.serializer())
            ?.toModel() ?: emptyList()

    /**
     * Default year as specified by the server.
     */
    override val defaultSeason: Int by lazy {
        configManager.getString(keyDefaultYear)?.toIntOrNull() ?: LocalDate.now().year
    }

    /**
     * Supported seasons
     */
    override val supportedSeasons: Set<Int>
        get() = configManager
            .getJson<SupportedSeasonsJson>(keySupportedSeasons, SupportedSeasonsJson.serializer())
            ?.convert()
            ?: emptySet()

    /**
     * Banner to be displayed at the top of the screen
     */
    override val dataProvidedBy: String?
        get() = configManager.getString(keyDataProvidedBy)

    companion object {
        // Config
        private const val keyDefaultYear: String = "default_year"
        private const val keyDefaultBanners: String = "banners"
        private const val keyDataProvidedBy: String = "data_provided"
        private const val keySupportedSeasons: String = "supported_seasons"
    }
}