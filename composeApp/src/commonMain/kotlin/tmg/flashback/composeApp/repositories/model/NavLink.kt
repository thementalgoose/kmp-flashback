package tmg.flashback.composeApp.repositories.model

sealed interface NavLink {

    val id: String

    data class Url(
        val name: String,
        val description: String?,
        val url: String,
        override val id: String = "url_${url.hashCode()}"
    ): NavLink

    data class Info(
        val name: String,
        val description: String?,
        override val id: String = "info_${name.hashCode()}"
    ): NavLink
}