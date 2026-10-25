package tmg.flashback.composeApp.presentation.navigation

import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.navigation.Screen

data class AppNavigationUIState(
    val showRss: Boolean,
    val easterEggs: AppNavigationEasterEggs,
    val screen: Screen?,
    val intoSubNavigation: Boolean,
    val promptContentSync: Boolean,
    val promptSoftUpgrade: Boolean,
)

data class AppNavigationEasterEggs(
    val snow: Boolean,
    val summer: Boolean,
)