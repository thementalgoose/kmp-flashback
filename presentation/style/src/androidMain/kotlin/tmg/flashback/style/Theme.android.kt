package tmg.flashback.style

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import org.koin.java.KoinJavaComponent.getKoin
import tmg.flashback.style.theme.Theme

actual fun getColours(theme: Theme): ThemeColours {
    val context = getKoin().get<Context>()
    return when (theme) {
        Theme.Default -> {
            Log.i("Theme", "Applying regular theme ($theme)")
            ThemeColours(
                lightColours = lightColours,
                darkColours = darkColours
            )
        }
        Theme.MaterialYou -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.i("Theme", "Applying dynamic theme ($theme)")
            ThemeColours(
                lightColours = lightColours.dynamic(dynamicLightColorScheme(context), isLightMode = true),
                darkColours = darkColours.dynamic(dynamicDarkColorScheme(context), isLightMode = false)
            )
        }  else {
            Log.i("Theme", "Applying regular theme ($theme)")
            ThemeColours(
                lightColours = lightColours,
                darkColours = darkColours
            )
        }
    }
}

//internal actual object LocalDarkMode {
//
//    actual val current: Boolean
//        @Composable get() = (LocalConfiguration.current.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
//
//    @Composable
//    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
//        val new = if (value == null) {
//            LocalConfiguration.current
//        } else {
//            Configuration(LocalConfiguration.current).apply {
//                uiMode = when (value) {
//                    false -> (uiMode and UI_MODE_NIGHT_MASK.inv()) or UI_MODE_NIGHT_NO
//                    true -> (uiMode and UI_MODE_NIGHT_MASK.inv()) or UI_MODE_NIGHT_YES
//                }
//            }
//        }
//        return LocalConfiguration.provides(new)
//    }
//}