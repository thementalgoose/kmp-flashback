package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

@Composable
fun TextCaption(
    text: String,
    modifier: Modifier = Modifier,
    fontStyle: FontStyle = FontStyle.Normal,
    maxLines: Int = Int.MAX_VALUE,
    textColor: Color? = null,
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
            color = textColor ?: AppTheme.colors.onSurfaceVariant
        )
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TextCaption(
            text = "Caption"
        )
    }
}