package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.preview.PreviewDarkMode
import tmg.flashback.style.preview.PreviewLightMode

@Composable
fun TextCaption(
    text: String,
    modifier: Modifier = Modifier,
    fontStyle: FontStyle = FontStyle.Normal,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text,
        maxLines = maxLines,
        textAlign = textAlign,
        modifier = modifier,
        style = AppTheme.typography.caption.copy(
            fontStyle = fontStyle,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.contentTertiary
        )
    )
}

@Preview
@Composable
private fun PreviewLight() = PreviewLightMode {
    TextCaption(
        text = "Caption"
    )
}

@Preview
@Composable
private fun PreviewDark() = PreviewDarkMode {
    TextCaption(
        text = "Caption"
    )
}