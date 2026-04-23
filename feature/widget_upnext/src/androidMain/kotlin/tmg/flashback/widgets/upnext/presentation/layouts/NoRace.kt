package tmg.flashback.widgets.upnext.presentation.layouts

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.layout.Column
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.preview.PreviewAllSizes
import tmg.flashback.widgets.upnext.presentation.style.preview.PreviewPixel
import tmg.flashback.widgets.upnext.presentation.style.text.TextFeature

@Composable
internal fun NoRace(
    modifier: GlanceModifier = GlanceModifier,
) {
    val size = LocalSize.current
    Column(
        modifier = modifier
    ) {
        TextFeature("N/A")
    }
}

@Composable
@PreviewPixel
private fun PreviewPixel() {
    WidgetThemePreview {
        NoRace()
    }
}

@Composable
@PreviewAllSizes
private fun PreviewMinMax() {
    WidgetThemePreview {
        NoRace()
    }
}