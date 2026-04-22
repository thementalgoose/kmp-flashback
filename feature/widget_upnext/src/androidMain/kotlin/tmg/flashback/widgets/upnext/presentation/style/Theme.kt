package tmg.flashback.widgets.upnext.presentation.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth

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
    WidgetTheme {
        Column(modifier = GlanceModifier
            .fillMaxSize()
            .cornerRadius(8.dp)
            .background(GlanceTheme.colors.widgetBackground)
        ) {
            content()
        }
    }
}