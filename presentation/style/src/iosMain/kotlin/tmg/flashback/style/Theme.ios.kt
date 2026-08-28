package tmg.flashback.style

import androidx.compose.runtime.Composable
import tmg.flashback.style.theme.Theme

actual fun getColours(theme: Theme): ThemeColours {
    return ThemeColours(
        lightColours = lightColours,
        darkColours = darkColours
    )
}

@Composable
actual fun setSystemBarsIconColours(isLight: Boolean) {

}