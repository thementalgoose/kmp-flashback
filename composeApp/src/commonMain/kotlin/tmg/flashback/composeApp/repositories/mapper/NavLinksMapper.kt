package tmg.flashback.composeApp.repositories.mapper

import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.composeApp.repositories.model.NavLinkJson
import tmg.flashback.composeApp.repositories.model.NavLinksJson

private const val URL = "url"
private const val INFO = "info"

internal fun NavLinksJson.convert(): List<NavLink> {
    return this.nav.mapNotNull { it.convert() }
}

internal fun NavLinkJson.convert(): NavLink? {
    if (this.type == null) {
        return null
    }
    return when (type) {
        URL -> this.url()
        INFO -> this.info()
        else -> null
    }
}

private fun NavLinkJson.url(): NavLink.Url? {
    if (this.name != null && this.url != null) {
        return NavLink.Url(
            name = this.name,
            description = this.description,
            url = this.url
        )
    }
    return null
}

private fun NavLinkJson.info(): NavLink.Info? {
    if (this.name != null) {
        return NavLink.Info(
            name = this.name,
            description = this.description
        )
    }
    return null
}