package tmg.flashback.presentation

import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.dashboard_nav_calendar
import flashback.composeapp.generated.resources.dashboard_nav_constructor
import flashback.composeapp.generated.resources.dashboard_nav_drivers
import flashback.composeapp.generated.resources.dashboard_reaction
import flashback.composeapp.generated.resources.dashboard_rss
import flashback.composeapp.generated.resources.dashboard_search
import flashback.composeapp.generated.resources.dashboard_settings
import flashback.composeapp.generated.resources.dashboard_contact
import flashback.composeapp.generated.resources.dashboard_nav_circuits
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.dashboard_tab_results
import flashback.presentation.localisation.generated.resources.nav_calendar
import flashback.presentation.localisation.generated.resources.nav_constructors
import flashback.presentation.localisation.generated.resources.nav_contact
import flashback.presentation.localisation.generated.resources.nav_drivers
import flashback.presentation.localisation.generated.resources.nav_reaction_game
import flashback.presentation.localisation.generated.resources.nav_rss
import flashback.presentation.localisation.generated.resources.nav_search
import flashback.presentation.localisation.generated.resources.nav_settings
import flashback.presentation.localisation.generated.resources.search_category_circuits
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.navigation.Screen
import tmg.flashback.ui.navigation.NavigationItem

enum class MenuItem(
    val key: String
) {
    Results(key = "Results"),
    Calendar(key = "Calendar"),
    DriversStandings(key = "DriversStandings"),
    TeamsStandings(key = "TeamsStandings"),
    Circuits(key = "Circuits"),
    Rss(key = "Rss"),
    ReactionGame(key = "ReactionGame"),
    Settings(key = "Settings"),
    Contact(key = "Contact"),
}

val MenuItem.label: StringResource
    get() = when (this) {
        MenuItem.Results -> string.dashboard_tab_results
        MenuItem.Calendar -> string.nav_calendar
        MenuItem.DriversStandings -> string.nav_drivers
        MenuItem.TeamsStandings -> string.nav_constructors
        MenuItem.Circuits -> string.search_category_circuits
        MenuItem.Rss -> string.nav_rss
        MenuItem.ReactionGame -> string.nav_reaction_game
        MenuItem.Settings -> string.nav_settings
        MenuItem.Contact -> string.nav_contact
    }

val MenuItem.icon: DrawableResource
    get() = when (this) {
        MenuItem.Results -> Res.drawable.dashboard_nav_calendar
        MenuItem.Calendar -> Res.drawable.dashboard_nav_calendar
        MenuItem.DriversStandings -> Res.drawable.dashboard_nav_drivers
        MenuItem.TeamsStandings -> Res.drawable.dashboard_nav_constructor
        MenuItem.Circuits -> Res.drawable.dashboard_nav_circuits
        MenuItem.Rss -> Res.drawable.dashboard_rss
        MenuItem.ReactionGame -> Res.drawable.dashboard_reaction
        MenuItem.Settings -> Res.drawable.dashboard_settings
        MenuItem.Contact -> Res.drawable.dashboard_contact
    }

fun MenuItem.toNavigationItem(
    isSelected: Boolean? = null
) = NavigationItem(
    id = this.key,
    label = this.label,
    icon = this.icon,
    isSelected = isSelected
)

fun MenuItem.toScreen(): Screen {
    return when (this) {
        MenuItem.Results -> Screen.Calendar
        MenuItem.Calendar -> Screen.Calendar
        MenuItem.DriversStandings -> Screen.DriverStandings
        MenuItem.TeamsStandings -> Screen.TeamStandings
        MenuItem.Circuits -> Screen.Circuits
        MenuItem.Rss -> Screen.Rss
        MenuItem.ReactionGame -> Screen.ReactionGame
        MenuItem.Settings -> Screen.Settings
        MenuItem.Contact -> Screen.About
    }
}