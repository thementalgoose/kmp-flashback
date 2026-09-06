package tmg.flashback.style.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.compositionLocalOf

private val LocalDesktopLightMode = compositionLocalOf<Boolean?> { null }

actual object LocalDarkMode {
    actual val current: Boolean
        @Composable get() = LocalDesktopLightMode.current ?: !isSystemInDarkTheme()

    @Composable
    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
        return LocalDesktopLightMode.provides(value)
    }
}