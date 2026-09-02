package tmg.flashback.style.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf

private val localWebLightMode = compositionLocalOf<Boolean?> { null }

actual object LocalDarkMode {
    actual val current: Boolean
        @Composable get() = localWebLightMode.current ?: false

    @Composable
    actual infix fun provides(value: Boolean?): ProvidedValue<*> = localWebLightMode.provides(value)
}
