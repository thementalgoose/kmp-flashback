package tmg.flashback.analytics.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import org.koin.compose.koinInject
import tmg.flashback.analytics.manager.AnalyticsManager

@Composable
fun ScreenView(
    screenName: String,
    args: Map<String, String> = mapOf(),
    updateKey: Any? = Unit
) {
    val analyticsManager = koinInject<AnalyticsManager>()
    DisposableEffect(key1 = updateKey, effect = {
        analyticsManager.viewScreen(screenName, args)
        this.onDispose { }
    })
}