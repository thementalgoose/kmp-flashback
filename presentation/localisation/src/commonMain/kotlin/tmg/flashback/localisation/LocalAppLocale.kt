package tmg.flashback.localisation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-resource-environment.html#locale
 */

var customAppLocale by mutableStateOf<String?>(null)

expect object LocalAppLocale {
    val current: String @Composable get
    @Composable infix fun provides(value: String?): ProvidedValue<*>
}