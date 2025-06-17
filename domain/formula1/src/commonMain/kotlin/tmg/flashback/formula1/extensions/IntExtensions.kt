package tmg.flashback.formula1.extensions

import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

val Int.positionIcon: DrawableResource
    get() = when (this) {
        1 -> Res.drawable.ic_p1
        2 -> Res.drawable.ic_p2
        3 -> Res.drawable.ic_p3
        4 -> Res.drawable.ic_p4
        5 -> Res.drawable.ic_p5
        6 -> Res.drawable.ic_p6
        7 -> Res.drawable.ic_p7
        8 -> Res.drawable.ic_p8
        9 -> Res.drawable.ic_p9
        10 -> Res.drawable.ic_p10
        else -> Res.drawable.ic_p11_plus
    }