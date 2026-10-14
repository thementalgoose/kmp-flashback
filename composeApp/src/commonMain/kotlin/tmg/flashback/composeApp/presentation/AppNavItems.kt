package tmg.flashback.composeApp.presentation

import tmg.flashback.composeApp.presentation.MenuItem.Results
import tmg.flashback.composeApp.presentation.MenuItem.Calendar
import tmg.flashback.composeApp.presentation.MenuItem.Circuits
import tmg.flashback.composeApp.presentation.MenuItem.Contact
import tmg.flashback.composeApp.presentation.MenuItem.DriversStandings
import tmg.flashback.composeApp.presentation.MenuItem.Lineup
import tmg.flashback.composeApp.presentation.MenuItem.ReactionGame
import tmg.flashback.composeApp.presentation.MenuItem.Rss
import tmg.flashback.composeApp.presentation.MenuItem.Settings
import tmg.flashback.composeApp.presentation.MenuItem.StyleGuide
import tmg.flashback.composeApp.presentation.MenuItem.TeamsStandings
import tmg.flashback.composeApp.presentation.MenuItem.XR_Spacial
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavLineup
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavStyleGuide
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.Screen
import tmg.flashback.ui.navigation.NavigationItem

fun generateFloatingNavItems(
    currentlySelectedScreen: Screen?
): List<NavigationItem> = listOf(
    Calendar.toNavigationItem(currentlySelectedScreen == NavCalendar),
    DriversStandings.toNavigationItem(currentlySelectedScreen == NavDriverStandings),
    TeamsStandings.toNavigationItem(currentlySelectedScreen == NavTeamStandings)
)

fun generateNavPrimaryItems(
    currentlySelectedScreen: Screen?,
    groupAsResults: Boolean = false
): List<NavigationItem> = listOfNotNull(
    Calendar.toNavigationItem(currentlySelectedScreen == NavCalendar).takeIf { !groupAsResults },
    DriversStandings.toNavigationItem(currentlySelectedScreen == NavDriverStandings).takeIf { !groupAsResults },
    TeamsStandings.toNavigationItem(currentlySelectedScreen == NavTeamStandings).takeIf { !groupAsResults },
    Results.toNavigationItem(
        isSelected = currentlySelectedScreen in listOf(NavCalendar, NavDriverStandings, NavTeamStandings)
    ).takeIf { groupAsResults },
    Circuits.toNavigationItem(currentlySelectedScreen == NavCircuits),
    Lineup.toNavigationItem(currentlySelectedScreen == NavLineup),
)

fun generateNavSecondaryItems(
    currentlySelectedScreen: Screen?,
    showRss: Boolean
): List<NavigationItem> = listOfNotNull(
    Rss.toNavigationItem(currentlySelectedScreen == NavRss).takeIf { showRss },
    ReactionGame.toNavigationItem(currentlySelectedScreen == NavReactionGame),
    Settings.toNavigationItem(currentlySelectedScreen == NavSettings),
    Contact.toNavigationItem(currentlySelectedScreen == NavAbout)
)

fun generateNavTertiaryItems(
    currentlySelectedScreen: Screen?,
    showXr: Boolean,
    isXrSelected: Boolean
): List<NavigationItem> = listOfNotNull(
    StyleGuide.toNavigationItem(currentlySelectedScreen == NavStyleGuide).takeIf { Device.isDebug },
    XR_Spacial.toNavigationItem(isXrSelected).takeIf { showXr }
)