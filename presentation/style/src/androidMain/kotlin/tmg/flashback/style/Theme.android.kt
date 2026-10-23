package tmg.flashback.style

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import org.koin.java.KoinJavaComponent.getKoin
import tmg.flashback.style.theme.Theme

actual fun getColours(theme: Theme): ThemeColours {
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
            val context = getKoin().get<Context>()
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

@Composable
actual fun setSystemBarsIconColours(isLight: Boolean) {
    val context = LocalActivity.current ?: return
    val view = LocalView.current
    if (view.isInEditMode) return
    val configuration = LocalConfiguration.current // Triggers recomposition on orientation changes

    SideEffect {
        val controller = WindowCompat.getInsetsController(context.window, view)
        controller.isAppearanceLightStatusBars = isLight
        controller.isAppearanceLightNavigationBars = isLight
        view.post {
            controller.isAppearanceLightStatusBars = isLight
            controller.isAppearanceLightNavigationBars = isLight
        }
    }
}