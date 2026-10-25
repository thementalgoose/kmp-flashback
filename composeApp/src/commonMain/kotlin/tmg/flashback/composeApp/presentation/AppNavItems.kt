package tmg.flashback.composeApp.presentation

import tmg.flashback.composeApp.presentation.MenuItem.Calendar
import tmg.flashback.composeApp.presentation.MenuItem.Contact
import tmg.flashback.composeApp.presentation.MenuItem.DriversStandings
import tmg.flashback.composeApp.presentation.MenuItem.Info
import tmg.flashback.composeApp.presentation.MenuItem.Paddock
import tmg.flashback.composeApp.presentation.MenuItem.ReactionGame
import tmg.flashback.composeApp.presentation.MenuItem.Rss
import tmg.flashback.composeApp.presentation.MenuItem.Search
import tmg.flashback.composeApp.presentation.MenuItem.Settings
import tmg.flashback.composeApp.presentation.MenuItem.StyleGuide
import tmg.flashback.composeApp.presentation.MenuItem.TeamsStandings
import tmg.flashback.composeApp.presentation.MenuItem.XR_Spacial
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavInfo
import tmg.flashback.navigation.NavPaddock
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSearch
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavStyleGuide
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.Screen
import tmg.flashback.ui.navigation.NavigationItem

val floatingNavItems = listOf(
    NavCalendar,
    NavDriverStandings,
    NavTeamStandings,
    NavPaddock,
    NavSettings
)

fun generateFloatingNavItems(
    currentlySelectedScreen: Screen?
): List<NavigationItem> = listOf(
    Calendar.toNavigationItem(currentlySelectedScreen == NavCalendar),
    DriversStandings.toNavigationItem(currentlySelectedScreen == NavDriverStandings),
    TeamsStandings.toNavigationItem(currentlySelectedScreen == NavTeamStandings),
    Paddock.toNavigationItem(currentlySelectedScreen == NavPaddock),
    Settings.toNavigationItem(currentlySelectedScreen == NavSettings)
)

fun generateNavPrimaryItems(
    currentlySelectedScreen: Screen?,
): List<NavigationItem> = listOfNotNull(
    Calendar.toNavigationItem(currentlySelectedScreen == NavCalendar),
    DriversStandings.toNavigationItem(currentlySelectedScreen == NavDriverStandings),
    TeamsStandings.toNavigationItem(currentlySelectedScreen == NavTeamStandings),
)

fun generateNavSecondaryItems(
    currentlySelectedScreen: Screen?,
    showRss: Boolean
): List<NavigationItem> = listOfNotNull(
    Rss.toNavigationItem(currentlySelectedScreen == NavRss).takeIf { showRss },
    ReactionGame.toNavigationItem(currentlySelectedScreen == NavReactionGame),
    Search.toNavigationItem(currentlySelectedScreen is NavSearch),
    Settings.toNavigationItem(currentlySelectedScreen == NavSettings),
    Contact.toNavigationItem(currentlySelectedScreen == NavAbout)
)

fun generateNavTertiaryItems(
    currentlySelectedScreen: Screen?,
    showXr: Boolean,
    isXrSelected: Boolean
): List<NavigationItem> = listOfNotNull(
    StyleGuide.toNavigationItem(currentlySelectedScreen == NavStyleGuide).takeIf { Device.isDebug },
    Info.toNavigationItem(currentlySelectedScreen == NavInfo).takeIf { Device.isDebug },
    XR_Spacial.toNavigationItem(isXrSelected).takeIf { showXr }
)