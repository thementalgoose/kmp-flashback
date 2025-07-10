package tmg.flashback.style.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.LocalSystemTheme
import androidx.compose.ui.SystemTheme

@OptIn(InternalComposeUiApi::class)
actual object LocalDarkMode {
    actual val current: Boolean
        @Composable get() = LocalSystemTheme.current == SystemTheme.Light

    @Composable
    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
        val new = when(value) {
            true -> SystemTheme.Light
            false -> SystemTheme.Dark
            null -> LocalSystemTheme.current
        }

        return LocalSystemTheme.provides(new)
    }
}