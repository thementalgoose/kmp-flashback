package tmg.flashback.style.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme

private val LocalIosLightMode = compositionLocalOf<Boolean?> { null }

actual object LocalDarkMode {
    actual val current: Boolean
        @Composable get() = LocalIosLightMode.current ?: (currentSystemTheme != SystemTheme.DARK)

    @Composable
    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
        return LocalIosLightMode.provides(value)
    }
}