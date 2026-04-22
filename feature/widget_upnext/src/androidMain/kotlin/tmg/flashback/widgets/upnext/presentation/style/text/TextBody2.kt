package tmg.flashback.widgets.upnext.presentation.style.text

import androidx.compose.runtime.Composable
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
fun TextBody2(
    text: String,
    color: ColorProvider = GlanceTheme.colors.onBackground,
    weight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    textDecoration: TextDecoration = TextDecoration.None,
    modifier: GlanceModifier = GlanceModifier
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        style = TextStyle(
            color = color,
            fontSize = 14.sp,
            fontWeight = weight,
            textAlign = textAlign,
            textDecoration = textDecoration
        )
    )
}