package tmg.flashback.style.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-resource-environment.html#theme
 */
var customLightMode by mutableStateOf<Boolean?>(null)

expect object LocalDarkMode {
    val current: Boolean @Composable get
    @Composable infix fun provides(value: Boolean?): ProvidedValue<*>
}