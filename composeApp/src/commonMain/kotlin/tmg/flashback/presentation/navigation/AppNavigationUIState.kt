package tmg.flashback.presentation.navigation

import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.navigation.Screen

data class AppNavigationUIState(
    val showRss: Boolean,
    val easterEggs: AppNavigationEasterEggs,
    val screen: Screen?,
)

data class AppNavigationEasterEggs(
    val menuIcon: MenuIcons?,
    val snow: Boolean,
    val summer: Boolean,
    val ukraine: Boolean,
)