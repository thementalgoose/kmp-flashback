package tmg.flashback.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Calendar: Screen

    @Serializable
    data object DriverStandings: Screen

    @Serializable
    data object TeamStandings: Screen

    @Serializable
    data object Search: Screen

    @Serializable
    data class Circuit(
        val id: String,
        val name: String,
    ): Screen {
        companion object
    }

    @Serializable
    data class Constructor(
        val id: String,
        val name: String
    ): Screen {
        companion object
    }

    @Serializable
    data class Driver(
        val id: String,
        val name: String
    ): Screen {
        companion object
    }

    @Serializable
    data object Rss: Screen

    @Serializable
    data object ReactionGame: Screen

    @Serializable
    data object Settings: Screen

    @Serializable
    data object About: Screen
}