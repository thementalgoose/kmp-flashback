package tmg.flashback.composeApp.repositories.model

sealed interface NavLink {

    val id: String

    data class Url(
        val name: String,
        val url: String,
        override val id: String = "url_${url.hashCode()}"
    ): NavLink
}