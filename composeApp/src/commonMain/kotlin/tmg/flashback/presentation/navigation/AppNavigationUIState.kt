package tmg.flashback.presentation.navigation

import tmg.flashback.eastereggs.model.MenuIcons

data class AppNavigationUIState(
    val showRss: Boolean,
    val menuIcon: MenuIcons?,
    val snow: Boolean,
    val summer: Boolean,
    val ukraine: Boolean
)