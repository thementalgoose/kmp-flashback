package tmg.flashback.presentation.navigation

import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.navigation.Screen

data class AppNavigationUIState(
    val showRss: Boolean,
    val showSearch: Boolean,
    val easterEggs: AppNavigationEasterEggs,
    val screen: Screen?,
    val intoSubNavigation: Boolean,
    val promptContentSync: Boolean,
    val promptSoftUpgrade: Boolean
)

data class AppNavigationEasterEggs(
    val menuIcon: MenuIcons?,
    val snow: Boolean,
    val summer: Boolean,
    val ukraine: Boolean,
)