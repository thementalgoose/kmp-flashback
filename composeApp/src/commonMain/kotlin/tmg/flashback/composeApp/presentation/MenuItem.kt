package tmg.flashback.composeApp.presentation

import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.dashboard_contact
import flashback.composeapp.generated.resources.dashboard_contact_selected
import flashback.composeapp.generated.resources.dashboard_info
import flashback.composeapp.generated.resources.dashboard_info_selected
import flashback.composeapp.generated.resources.dashboard_nav_calendar
import flashback.composeapp.generated.resources.dashboard_nav_calendar_selected
import flashback.composeapp.generated.resources.dashboard_nav_constructor
import flashback.composeapp.generated.resources.dashboard_nav_constructor_selected
import flashback.composeapp.generated.resources.dashboard_nav_drivers
import flashback.composeapp.generated.resources.dashboard_nav_drivers_selected
import flashback.composeapp.generated.resources.dashboard_nav_paddock
import flashback.composeapp.generated.resources.dashboard_nav_paddock_selected
import flashback.composeapp.generated.resources.dashboard_settings
import flashback.composeapp.generated.resources.dashboard_settings_selected
import flashback.composeapp.generated.resources.dashboard_style_guide
import flashback.composeapp.generated.resources.dashboard_style_guide_selected
import flashback.composeapp.generated.resources.dashboard_xr
import flashback.composeapp.generated.resources.dashboard_xr_selected
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_calendar
import flashback.presentation.localisation.generated.resources.nav_constructors
import flashback.presentation.localisation.generated.resources.nav_contact
import flashback.presentation.localisation.generated.resources.nav_drivers
import flashback.presentation.localisation.generated.resources.nav_info
import flashback.presentation.localisation.generated.resources.nav_paddock
import flashback.presentation.localisation.generated.resources.nav_settings
import flashback.presentation.localisation.generated.resources.nav_style_guide
import flashback.presentation.localisation.generated.resources.nav_xr
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavInfo
import tmg.flashback.navigation.NavPaddock
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavStyleGuide
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.Screen
import tmg.flashback.ui.navigation.NavigationItem

enum class MenuItem(
    val key: String
) {
    // Main tabs
    Calendar(key = "Calendar"),
    DriversStandings(key = "DriversStandings"),
    TeamsStandings(key = "TeamsStandings"),
    Paddock(key = "Paddock"),
    Settings(key = "Settings"),

    // Extra options (visible in expanded views)
    Contact(key = "Contact"),
    StyleGuide(key = "Style Guide"),
    Info(key = "Debug Info"),
    XR_Spacial(key = "XR_Spacial"),
}

val MenuItem.label: StringResource
    get() = when (this) {
        MenuItem.Calendar -> string.nav_calendar
        MenuItem.DriversStandings -> string.nav_drivers
        MenuItem.TeamsStandings -> string.nav_constructors
        MenuItem.Paddock -> string.nav_paddock
        MenuItem.Settings -> string.nav_settings
        MenuItem.Contact -> string.nav_contact
        MenuItem.StyleGuide -> string.nav_style_guide
        MenuItem.Info -> string.nav_info
        MenuItem.XR_Spacial -> string.nav_xr
    }

val MenuItem.icon: DrawableResource
    get() = when (this) {
        MenuItem.Calendar -> Res.drawable.dashboard_nav_calendar
        MenuItem.DriversStandings -> Res.drawable.dashboard_nav_drivers
        MenuItem.TeamsStandings -> Res.drawable.dashboard_nav_constructor
        MenuItem.Paddock -> Res.drawable.dashboard_nav_paddock
        MenuItem.Settings -> Res.drawable.dashboard_settings
        MenuItem.Contact -> Res.drawable.dashboard_contact
        MenuItem.StyleGuide -> Res.drawable.dashboard_style_guide
        MenuItem.Info -> Res.drawable.dashboard_info
        MenuItem.XR_Spacial -> Res.drawable.dashboard_xr
    }

val MenuItem.selectedIcon: DrawableResource
    get() = when (this) {
        MenuItem.Calendar -> Res.drawable.dashboard_nav_calendar_selected
        MenuItem.DriversStandings -> Res.drawable.dashboard_nav_drivers_selected
        MenuItem.TeamsStandings -> Res.drawable.dashboard_nav_constructor_selected
        MenuItem.Paddock -> Res.drawable.dashboard_nav_paddock_selected
        MenuItem.Settings -> Res.drawable.dashboard_settings_selected
        MenuItem.Contact -> Res.drawable.dashboard_contact_selected
        MenuItem.StyleGuide -> Res.drawable.dashboard_style_guide_selected
        MenuItem.Info -> Res.drawable.dashboard_info_selected
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

fun MenuItem.toScreen(): Screen? {
    return when (this) {
        MenuItem.Calendar -> NavCalendar
        MenuItem.DriversStandings -> NavDriverStandings
        MenuItem.TeamsStandings -> NavTeamStandings
        MenuItem.Paddock -> NavPaddock
        MenuItem.Settings -> NavSettings
        MenuItem.StyleGuide -> NavStyleGuide
        MenuItem.Info -> NavInfo
        MenuItem.Contact -> NavAbout
        MenuItem.XR_Spacial -> null
    }
}