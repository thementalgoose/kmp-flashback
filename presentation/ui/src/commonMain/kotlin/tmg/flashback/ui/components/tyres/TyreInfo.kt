package tmg.flashback.ui.components.tyres

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class TyreInfo(
    val icon: DrawableResource,
    val label: StringResource,
    val size: Int,
)