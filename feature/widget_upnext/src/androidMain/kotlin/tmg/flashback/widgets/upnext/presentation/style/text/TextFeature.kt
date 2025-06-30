package tmg.flashback.widgets.upnext.presentation.style.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@Composable
fun TextFeature(
    text: String,
    color: Color = Color.White,
    weight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 32.sp,
    modifier: GlanceModifier = GlanceModifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = ColorProvider(color),
            fontSize = fontSize,
            fontWeight = weight,
            textAlign = textAlign
        )
    )
}

