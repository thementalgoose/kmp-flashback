package tmg.flashback.localisation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf

actual object LocalAppLocale {
    private val locale = staticCompositionLocalOf { "en-US" }

    actual val current: String
        @Composable get() = locale.current

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> = locale.provides(value ?: "en-US")
}
