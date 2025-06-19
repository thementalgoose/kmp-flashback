package tmg.flashback.navigation

sealed class Screen {

    data object Races: Screen()

    data object DriverStandings: Screen()

    data object ConstructorStandings: Screen()

    data object Search: Screen()

    data class Circuit(
        val circuitId: String,
        val circuitName: String,
    ): Screen() {
        companion object
    }

    data class Constructor(
        val constructorId: String,
        val constructorName: String
    ): Screen() {
        companion object
    }

    data class Driver(
        val driverId: String,
        val driverName: String
    ): Screen() {
        companion object
    }

    data object Rss: Screen()

    data object ReactionGame: Screen()

    data object Settings: Screen()

    data object PrivacyPolicy: Screen()

}