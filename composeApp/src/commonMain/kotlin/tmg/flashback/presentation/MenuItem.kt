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
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_calendar
import flashback.presentation.localisation.generated.resources.nav_constructors
import flashback.presentation.localisation.generated.resources.nav_contact
import flashback.presentation.localisation.generated.resources.nav_drivers
import flashback.presentation.localisation.generated.resources.nav_reaction_game
import flashback.presentation.localisation.generated.resources.nav_rss
import flashback.presentation.localisation.generated.resources.nav_search
import flashback.presentation.localisation.generated.resources.nav_settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class MenuItem {
    Calendar,
    DriversStandings,
    TeamsStandings,
    Search,
    Rss,
    ReactionGame,
    Settings,
    Contact
}

val MenuItem.label: StringResource
    get() = when (this) {
        MenuItem.Calendar -> string.nav_calendar
        MenuItem.DriversStandings -> string.nav_drivers
        MenuItem.TeamsStandings -> string.nav_constructors
        MenuItem.Search -> string.nav_search
        MenuItem.Rss -> string.nav_rss
        MenuItem.ReactionGame -> string.nav_reaction_game
        MenuItem.Settings -> string.nav_settings
        MenuItem.Contact -> string.nav_contact
    }

val MenuItem.icon: DrawableResource
    get() = when (this) {
        MenuItem.Calendar -> Res.drawable.dashboard_nav_calendar
        MenuItem.DriversStandings -> Res.drawable.dashboard_nav_drivers
        MenuItem.TeamsStandings -> Res.drawable.dashboard_nav_constructor
        MenuItem.Search -> Res.drawable.dashboard_search
        MenuItem.Rss -> Res.drawable.dashboard_rss
        MenuItem.ReactionGame -> Res.drawable.dashboard_reaction
        MenuItem.Settings -> Res.drawable.dashboard_settings
        MenuItem.Contact -> Res.drawable.dashboard_contact
    }