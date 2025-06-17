package tmg.flashback.formula1.enums

import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

enum class Tyre(
    val icon: DrawableResource,
    val isDry: Boolean,
    val size: Int
) {
    // 13" DRY
    RED_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_red_dry,
        isDry = true,
        size = 13
    ),
    YELLOW_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_yellow_dry,
        isDry = true,
        size = 13
    ),
    WHITE_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_white_dry,
        isDry = true,
        size = 13
    ),
    GRAY_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_gray_dry,
        isDry = true,
        size = 13
    ),
    BLUE_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_blue_dry,
        isDry = true,
        size = 13
    ),
    PURPLE_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_purple_dry,
        isDry = true,
        size = 13
    ),
    PINK_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_pink_dry,
        isDry = true,
        size = 13
    ),
    ORANGE_DRY_13(
        icon = Res.drawable.tyre_pirelli_13_orange_dry,
        isDry = true,
        size = 13
    ),

    // 13" WET
    ORANGE_WET_13(
        icon = Res.drawable.tyre_pirelli_13_orange_wet,
        isDry = false,
        size = 13,
    ),
    BLUE_WET_13(
        icon = Res.drawable.tyre_pirelli_13_blue_wet,
        isDry = false,
        size = 13,
    ),
    GREEN_WET_13(
        icon = Res.drawable.tyre_pirelli_13_green_wet,
        isDry = false,
        size = 13,
    ),

    // 18" DRY
    RED_DRY_18(
        icon = Res.drawable.tyre_pirelli_18_red_dry,
        isDry = true,
        size = 18
    ),
    YELLOW_DRY_18(
        icon = Res.drawable.tyre_pirelli_18_yellow_dry,
        isDry = true,
        size = 18
    ),
    WHITE_DRY_18(
        icon = Res.drawable.tyre_pirelli_18_white_dry,
        isDry = true,
        size = 18
    ),

    // 18" WET
    GREEN_WET_18(
        icon = Res.drawable.tyre_pirelli_18_green_wet,
        isDry = false,
        size = 18
    ),
    BLUE_WET_18(
        icon = Res.drawable.tyre_pirelli_18_blue_wet,
        isDry = false,
        size = 18
    )
}