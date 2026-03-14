package tmg.flashback.analytics.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import org.koin.compose.koinInject
import tmg.flashback.analytics.manager.AnalyticsManager

@Composable
fun ScreenView(
    screenName: String,
    args: Map<String, String> = mapOf(),
    updateKey: Any? = Unit,
    updateKey2: Any? = Unit,
    shouldReport: () -> Boolean = { true }
) {
    val analyticsManager: AnalyticsManager = koinInject()
    DisposableEffect(key1 = updateKey, key2 = updateKey2, effect = {
        if (shouldReport()) {
            analyticsManager.viewScreen(screenName, args)
        }
        this.onDispose { }
    })
}