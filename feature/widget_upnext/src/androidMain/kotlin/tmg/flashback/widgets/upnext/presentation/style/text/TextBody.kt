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
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@Composable
fun TextBody(
    text: String,
    color: ColorProvider = GlanceTheme.colors.onBackground,
    size: TextUnit = 14.sp,
    weight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration = TextDecoration.None,
    modifier: GlanceModifier = GlanceModifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = color,
            fontSize = size,
            fontWeight = weight,
            textAlign = textAlign,
            textDecoration = textDecoration
        )
    )
}