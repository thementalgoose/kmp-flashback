package tmg.flashback.style.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.preview.PreviewDarkMode
import tmg.flashback.style.preview.PreviewLightMode

@Composable
fun TextBody1(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    bold: Boolean = false,
    textColor: Color? = null,
    maxLines: Int? = null
) {
    Text(
        text,
        modifier = modifier,
        textAlign = textAlign,
        maxLines = maxLines ?: Int.MAX_VALUE,
        overflow = if (maxLines != null) TextOverflow.Ellipsis else TextOverflow.Clip,
        style = AppTheme.typography.body1.copy(
            fontWeight = when (bold) {
                true -> FontWeight.Bold
                false -> FontWeight.Normal
            },
            color = textColor ?: AppTheme.colors.contentPrimary
        )
    )
}

@Preview
@Composable
private fun PreviewLight() = PreviewLightMode {
    TextBody1(
        text = "Body 1"
    )
}

@Preview
@Composable
private fun PreviewDark() = PreviewDarkMode {
    TextBody1(
        text = "Body 1"
    )
}

@Preview
@Composable
private fun PreviewBoldLight() = PreviewLightMode {
    TextBody1(
        text = "Body 1 Bold",
        bold = true
    )
}

@Preview
@Composable
private fun PreviewBoldDark() = PreviewDarkMode {
    TextBody1(
        text = "Body 1 Bold",
        bold = true
    )
}