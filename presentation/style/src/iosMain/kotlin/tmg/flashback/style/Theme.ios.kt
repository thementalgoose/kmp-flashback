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

//@OptIn(InternalComposeUiApi::class)
//actual object LocalDarkMode {
//
//    actual val current: Boolean
//        @Composable get() = LocalSystemTheme.current == SystemTheme.Dark
//
//    @Composable
//    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
//        val new = when(value) {
//            true -> SystemTheme.Dark
//            false -> SystemTheme.Light
//            null -> LocalSystemTheme.current
//        }
//
//        return LocalSystemTheme.provides(new)
//    }
//}