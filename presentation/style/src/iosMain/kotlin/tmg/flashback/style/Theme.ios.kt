package tmg.flashback.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.LocalSystemTheme
import androidx.compose.ui.SystemTheme
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