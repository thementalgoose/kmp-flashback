package tmg.flashback.widgets.upnext.presentation.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.background
import androidx.glance.layout.Column

@Composable
fun WidgetTheme(
    content: @Composable () -> Unit
) {
    GlanceTheme(colors = GlanceTheme.colors) {
        content()
    }
}

@Composable
fun WidgetThemePreview(
    isLight: Boolean = true,
    content: @Composable () -> Unit,
) {
    val backgroundColor = when (isLight) {
        true -> Color.Black
        false -> Color.White
    }
    Column(modifier = GlanceModifier.background(backgroundColor)) {
        WidgetTheme(content)
    }
}