package tmg.flashback.widgets.upnext.presentation.style.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@Composable
fun TextFeature(
    text: String,
    color: ColorProvider = GlanceTheme.colors.onBackground,
    weight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 24.sp,
    maxLines: Int = Int.MAX_VALUE,
    modifier: GlanceModifier = GlanceModifier
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = weight,
            textAlign = textAlign
        )
    )
}

