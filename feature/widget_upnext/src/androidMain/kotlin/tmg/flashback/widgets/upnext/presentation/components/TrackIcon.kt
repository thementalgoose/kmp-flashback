package tmg.flashback.widgets.upnext.presentation.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentHeight
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import tmg.flashback.feature.widget_upnext.R
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.utils.BitmapUtils.getBitmapFromVectorDrawable
import tmg.flashback.widgets.upnext.utils.icon

@Composable
internal fun TrackIcon(
    context: Context,
    season: Int,
    raceName: String,
    circuitId: String,
    circuitName: String,
    trackColour: Color,
    modifier: GlanceModifier = GlanceModifier
        .width(100.dp)
        .wrapContentHeight()
) {
    val trackLayout = TrackLayout.getTrack(circuitId)?.icon ?: R.drawable.widget_circuit_unknown
    val bitmap = getBitmapFromVectorDrawable(context, trackLayout, trackColour.toArgb())
    if (bitmap != null) {
        Image(
            provider = ImageProvider(bitmap),
            contentDescription = circuitName,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun Preview() {
    WidgetThemePreview(
        isLight = false
    ) {
        TrackIcon(
            context = LocalContext.current,
            season = 2020,
            raceName = "Emilia Romagna Grand Prix",
            circuitId = "imola",
            circuitName = "Imola Circuit",
            trackColour = Color.White
        )
    }
}