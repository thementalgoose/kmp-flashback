package tmg.flashback.composeApp.repositories

import tmg.flashback.composeApp.repositories.mapper.convert
import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.composeApp.repositories.model.NavLinksJson
import tmg.flashback.configuration.manager.ConfigManager

interface NavRepository {
    val navLinks: List<NavLink>
}

internal class NavRepositoryImpl(
    private val configManager: ConfigManager,
): NavRepository {

    override val navLinks: List<NavLink> by lazy {
        configManager
            .getJson(keyNav, NavLinksJson.serializer())
            ?.convert()
            ?: emptyList()
    }

    companion object {

        private const val keyNav: String = "nav"
    }
}