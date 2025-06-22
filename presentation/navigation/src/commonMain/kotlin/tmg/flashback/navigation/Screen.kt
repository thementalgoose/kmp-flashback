package tmg.flashback.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Results: Screen

    @Serializable
    data object Search: Screen

    @Serializable
    data class Circuit(
        val circuitId: String,
        val circuitName: String,
    ): Screen {
        companion object
    }

    @Serializable
    data class Constructor(
        val constructorId: String,
        val constructorName: String
    ): Screen {
        companion object
    }

    @Serializable
    data class Driver(
        val driverId: String,
        val driverName: String
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