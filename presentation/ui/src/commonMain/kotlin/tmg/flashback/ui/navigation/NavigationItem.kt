package tmg.flashback.ui.navigation

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class NavigationItem(
    val id: String,
    val label: StringResource,
    val icon: DrawableResource,
    val isSelected: Boolean? = false,
)

internal val fakeNavigationItems: List<NavigationItem> = listOf(
    NavigationItem(
        id = "menu",
        label = string.ab_menu,
        icon = Res.drawable.ic_nightmode_dark,
        isSelected = true
    ),
    NavigationItem(
        id = "back",
        label = string.ab_back,
        icon = Res.drawable.ic_theme_material_you
    ),
    NavigationItem(
        id = "settings",
        label = string.settings_theme_title,
        icon = Res.drawable.ic_nightmode_auto
    ),
    NavigationItem(
        id = "light",
        label = string.settings_theme_nightmode_light,
        icon = Res.drawable.ic_nightmode_light
    ),
    NavigationItem(
        id = "experiment",
        label = string.settings_experimental,
        icon = Res.drawable.ic_theme_default
    )
)