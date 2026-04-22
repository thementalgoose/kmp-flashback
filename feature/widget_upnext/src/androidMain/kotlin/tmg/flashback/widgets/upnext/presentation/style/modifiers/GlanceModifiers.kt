package tmg.flashback.widgets.upnext.presentation.style.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.unit.ColorProvider

@Composable
fun GlanceModifier.surface(color: ColorProvider): GlanceModifier = this
    .fillMaxSize()
    .background(color)
    .padding(0.dp)

