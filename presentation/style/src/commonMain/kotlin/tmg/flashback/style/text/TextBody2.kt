package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

@Composable
fun TextBody2(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    bold: Boolean = false,
    italic: Boolean = false,
    textColor: Color? = null,
    textDecoration: TextDecoration? = null,
    maxLines: Int? = null
) {
    TextBody2(
        annotatedString = AnnotatedString(text),
        modifier = modifier,
        textAlign = textAlign,
        bold = bold,
        italic = italic,
        textColor = textColor,
        textDecoration = textDecoration,
        maxLines = maxLines,
    )
}

@Composable
fun TextBody2(
    annotatedString: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    bold: Boolean = false,
    italic: Boolean = false,
    textColor: Color? = null,
    textDecoration: TextDecoration? = null,
    maxLines: Int? = null
) {
    Text(
        annotatedString,
        modifier = modifier,
        textAlign = textAlign,
        maxLines = maxLines ?: Int.MAX_VALUE,
        overflow = if (maxLines != null) TextOverflow.Ellipsis else TextOverflow.Clip,
        style = AppTheme.typography.body2.copy(
            fontWeight = when (bold) {
                true -> FontWeight.Bold
                false -> FontWeight.Normal
            },
            color = textColor ?: AppTheme.colors.onSurfaceVariant
        ),
        textDecoration = textDecoration,
        fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TextBody2(
            text = "Body 2"
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewUnderlined() {
    ApplicationThemePreview {
        TextBody2(
            text = "Body 2",
            textDecoration = TextDecoration.Underline
        )
    }
}