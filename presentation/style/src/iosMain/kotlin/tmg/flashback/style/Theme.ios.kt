package tmg.flashback.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle
import tmg.flashback.style.theme.Theme

actual fun getColours(theme: Theme): ThemeColours {
    return ThemeColours(
        lightColours = lightColours,
        darkColours = darkColours
    )
}

@Composable
actual fun setSystemBarsIconColours(isLight: Boolean) {
    SideEffect {
        val style = if (isLight) {
            UIStatusBarStyleDarkContent
        } else {
            UIStatusBarStyleLightContent
        }
        UIApplication.sharedApplication.setStatusBarStyle(style, animated = true)
    }
}