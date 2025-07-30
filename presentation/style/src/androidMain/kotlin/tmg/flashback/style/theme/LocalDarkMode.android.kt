package tmg.flashback.style.theme

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration

actual object LocalDarkMode {
    actual val current: Boolean
        @Composable get() = (LocalConfiguration.current.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_NO

    @Composable
    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
        val new = if (value == null) {
            LocalConfiguration.current
        } else {
            Configuration(LocalConfiguration.current).apply {
                uiMode = when (value) {
                    false -> (uiMode and UI_MODE_NIGHT_MASK.inv()) or UI_MODE_NIGHT_YES
                    true -> (uiMode and UI_MODE_NIGHT_MASK.inv()) or UI_MODE_NIGHT_NO
                }
            }
        }
        return LocalConfiguration.provides(new)
    }
}
