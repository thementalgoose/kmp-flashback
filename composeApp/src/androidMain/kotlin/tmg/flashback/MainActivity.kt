package tmg.flashback

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.android.inject
import tmg.flashback.style.theme.Theme
import tmg.flashback.style.theme.ThemeManager
import tmg.flashback.ui.activity.ActivityProvider
import tmg.flashback.ui.activity.BaseActivity

class MainActivity : BaseActivity(), SplashScreen.KeepOnScreenCondition {

    private val themeManager: ThemeManager by inject()
    private val theme = when (themeManager.currentTheme) {
        Theme.Default -> R.style.FlashbackAppTheme_Default
        Theme.MaterialYou -> R.style.FlashbackAppTheme_MaterialYou
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setTheme(theme)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setSplashScreenTheme(theme)
        }
        val splashScreen = installSplashScreen()
        setTheme(theme)
        splashScreen.setKeepOnScreenCondition(this)

        setContent {
            App()
        }
    }

    override fun shouldKeepOnScreen(): Boolean = false
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}