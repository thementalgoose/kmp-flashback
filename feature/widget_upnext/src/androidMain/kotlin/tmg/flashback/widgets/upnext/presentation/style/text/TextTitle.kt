package tmg.flashback.widgets.upnext.presentation.style.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@Composable
fun TextTitle(
    text: String,
    color: ColorProvider = GlanceTheme.colors.onBackground,
    weight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    modifier: GlanceModifier = GlanceModifier
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        style = TextStyle(
            color = color,
            fontSize = 18.sp,
            fontWeight = weight,
            textAlign = textAlign
        )
    )
}