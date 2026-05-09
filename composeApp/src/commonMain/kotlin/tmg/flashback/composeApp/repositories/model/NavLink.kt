package tmg.flashback.composeApp.repositories.model

sealed interface NavLink {
    data class Url(
        val name: String,
        val url: String
    ): NavLink
}