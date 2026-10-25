package tmg.flashback.eastereggs.presentation

import flashback.eastereggs.generated.resources.Res
import flashback.eastereggs.generated.resources.ic_easteregg_bonfire
import flashback.eastereggs.generated.resources.ic_easteregg_christmas
import flashback.eastereggs.generated.resources.ic_easteregg_diwali
import flashback.eastereggs.generated.resources.ic_easteregg_easter
import flashback.eastereggs.generated.resources.ic_easteregg_halloween
import flashback.eastereggs.generated.resources.ic_easteregg_newyears
import flashback.eastereggs.generated.resources.ic_easteregg_valentines
import org.jetbrains.compose.resources.DrawableResource
import tmg.flashback.eastereggs.model.MenuIcons

val MenuIcons.drawable: DrawableResource get() = when (this) {
    MenuIcons.VALENTINES_DAY -> Res.drawable.ic_easteregg_valentines
    MenuIcons.EASTER -> Res.drawable.ic_easteregg_easter
    MenuIcons.HALLOWEEN -> Res.drawable.ic_easteregg_halloween
    MenuIcons.BONFIRE -> Res.drawable.ic_easteregg_bonfire
    MenuIcons.CHRISTMAS -> Res.drawable.ic_easteregg_christmas
    MenuIcons.NEW_YEARS -> Res.drawable.ic_easteregg_newyears
    MenuIcons.DIWALI -> Res.drawable.ic_easteregg_diwali
    MenuIcons.CHINESE_NEW_YEAR -> Res.drawable.ic_easteregg_newyears
    MenuIcons.NEW_YEARS_EVE -> Res.drawable.ic_easteregg_newyears
}