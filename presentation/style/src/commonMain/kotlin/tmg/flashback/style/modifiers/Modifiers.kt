package tmg.flashback.style.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import tmg.flashback.style.AppTheme.disabledAlpha

fun Modifier.appearDisabled(
    enabled: Boolean = false
): Modifier {
    return this.alpha(if (enabled) 1f else disabledAlpha)
}