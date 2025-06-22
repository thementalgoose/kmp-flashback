package tmg.flashback

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import tmg.flashback.presentation.AppContainer
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.components.AppScaffold

@Composable
fun App() {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    AppTheme {
        AppScaffold(
            content = {
                AppContainer(
                    windowAdaptiveInfo = windowAdaptiveInfo
                )
            }
        )
    }
}