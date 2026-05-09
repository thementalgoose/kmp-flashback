package tmg.flashback.composeApp.repositories.mapper

import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.composeApp.repositories.model.NavLinkJson
import tmg.flashback.composeApp.repositories.model.NavLinksJson

internal fun NavLinksJson.convert(): List<NavLink> {
    return this.nav.mapNotNull { it.convert() }
}

internal fun NavLinkJson.convert(): NavLink? {
    if (this.url == null || this.type == null || this.name == null) {
        return null
    }
    return when (type) {
        "url" -> NavLink.Url(
            name = this.name,
            url = this.url
        )
        else -> null
    }
}