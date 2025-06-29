package tmg.flashback.ui.extensions

import androidx.compose.ui.graphics.Color

fun Color.manipulate(colourPercent: Float = 1f): Color {
    return Color(
        red = this.red * colourPercent,
        green = this.green * colourPercent,
        blue = this.blue * colourPercent,
        alpha = this.alpha,
        colorSpace = this.colorSpace
    )
}