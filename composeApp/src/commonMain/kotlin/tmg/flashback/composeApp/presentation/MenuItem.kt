package tmg.flashback.composeApp.presentation

import androidx.navigation3.runtime.NavKey
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.dashboard_nav_calendar
import flashback.composeapp.generated.resources.dashboard_nav_constructor
import flashback.composeapp.generated.resources.dashboard_nav_drivers
import flashback.composeapp.generated.resources.dashboard_reaction
import flashback.composeapp.generated.resources.dashboard_rss
import flashback.composeapp.generated.resources.dashboard_xr
import flashback.composeapp.generated.resources.dashboard_settings
import flashback.composeapp.generated.resources.dashboard_contact
import flashback.composeapp.generated.resources.dashboard_contact_selected
import flashback.composeapp.generated.resources.dashboard_nav_calendar_selected
import flashback.composeapp.generated.resources.dashboard_nav_circuits
import flashback.composeapp.generated.resources.dashboard_nav_circuits_selected
import flashback.composeapp.generated.resources.dashboard_nav_constructor_selected
import flashback.composeapp.generated.resources.dashboard_nav_drivers_selected
import flashback.composeapp.generated.resources.dashboard_nav_results
import flashback.composeapp.generated.resources.dashboard_nav_results_selected
import flashback.composeapp.generated.resources.dashboard_reaction_selected
import flashback.composeapp.generated.resources.dashboard_rss_selected
import flashback.composeapp.generated.resources.dashboard_settings_selected
import flashback.composeapp.generated.resources.dashboard_xr_selected
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.dashboard_tab_results
import flashback.presentation.localisation.generated.resources.nav_calendar
import flashback.presentation.localisation.generated.resources.nav_constructors
import flashback.presentation.localisation.generated.resources.nav_contact
import flashback.presentation.localisation.generated.resources.nav_drivers
import flashback.presentation.localisation.generated.resources.nav_reaction_game
import flashback.presentation.localisation.generated.resources.nav_rss
import flashback.presentation.localisation.generated.resources.nav_search
import flashback.presentation.localisation.generated.resources.nav_xr
import flashback.presentation.localisation.generated.resources.nav_settings
import flashback.presentation.localisation.generated.resources.search_category_circuits
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavTeamStandings
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
    XR_Spacial(key = "XR_Spacial")
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
        MenuItem.XR_Spacial -> string.nav_xr
    }

val MenuItem.icon: DrawableResource
    get() = when (this) {
        MenuItem.Results -> Res.drawable.dashboard_nav_results
        MenuItem.Calendar -> Res.drawable.dashboard_nav_calendar
        MenuItem.DriversStandings -> Res.drawable.dashboard_nav_drivers
        MenuItem.TeamsStandings -> Res.drawable.dashboard_nav_constructor
        MenuItem.Circuits -> Res.drawable.dashboard_nav_circuits
        MenuItem.Rss -> Res.drawable.dashboard_rss
        MenuItem.ReactionGame -> Res.drawable.dashboard_reaction
        MenuItem.Settings -> Res.drawable.dashboard_settings
        MenuItem.Contact -> Res.drawable.dashboard_contact
        MenuItem.XR_Spacial -> Res.drawable.dashboard_xr
    }

val MenuItem.selectedIcon: DrawableResource
    get() = when (this) {
        MenuItem.Results -> Res.drawable.dashboard_nav_results_selected
        MenuItem.Calendar -> Res.drawable.dashboard_nav_calendar_selected
        MenuItem.DriversStandings -> Res.drawable.dashboard_nav_drivers_selected
        MenuItem.TeamsStandings -> Res.drawable.dashboard_nav_constructor_selected
        MenuItem.Circuits -> Res.drawable.dashboard_nav_circuits_selected
        MenuItem.Rss -> Res.drawable.dashboard_rss_selected
        MenuItem.ReactionGame -> Res.drawable.dashboard_reaction_selected
        MenuItem.Settings -> Res.drawable.dashboard_settings_selected
        MenuItem.Contact -> Res.drawable.dashboard_contact_selected
        MenuItem.XR_Spacial -> Res.drawable.dashboard_xr_selected
    }

fun MenuItem.toNavigationItem(
    isSelected: Boolean? = null
) = NavigationItem(
    id = this.key,
    label = this.label,
    icon = this.icon,
    selectedIcon = this.selectedIcon,
    isSelected = isSelected
)

fun MenuItem.toScreen(): NavKey? {
    return when (this) {
        MenuItem.Results -> NavCalendar
        MenuItem.Calendar -> NavCalendar
        MenuItem.DriversStandings -> NavDriverStandings
        MenuItem.TeamsStandings -> NavTeamStandings
        MenuItem.Circuits -> NavCircuits
        MenuItem.Rss -> NavRss
        MenuItem.ReactionGame -> NavReactionGame
        MenuItem.Settings -> NavSettings
        MenuItem.Contact -> NavAbout
        MenuItem.XR_Spacial -> null
    }
}