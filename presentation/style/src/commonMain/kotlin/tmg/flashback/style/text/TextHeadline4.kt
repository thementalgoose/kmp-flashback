package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme


@Composable
fun TextHeadline4(
    text: String,
    modifier: Modifier = Modifier,
    mono: Boolean = false,
    bold: Boolean = false,
    italic: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    colourType: ColourType = ColourType.DEFAULT
) {
    val style = when (colourType) {
        ColourType.DEFAULT -> AppTheme.typography.h4.copy(
            fontFamily = if (mono) FontFamily.Monospace else AppTheme.typography.h4.fontFamily,
            color = AppTheme.colors.onSurface,
            textAlign = textAlign,
            fontWeight = if (bold) FontWeight.Black else FontWeight.SemiBold,
            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
        )
        ColourType.BRAND -> AppTheme.typography.h4.copy(
            fontFamily = if (mono) FontFamily.Monospace else AppTheme.typography.h4.fontFamily,
            color = AppTheme.colors.primary,
            textAlign = textAlign,
            fontWeight = if (bold) FontWeight.Black else FontWeight.SemiBold,
            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
        )
        ColourType.RAINBOW -> AppTheme.typography.h4.copy(
            fontFamily = if (mono) FontFamily.Monospace else AppTheme.typography.h4.fontFamily,
            brush = Brush.horizontalGradient(RainbowColors),
            textAlign = textAlign,
            fontWeight = if (bold) FontWeight.Black else FontWeight.SemiBold,
            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
        )
    }
    Text(
        text,
        modifier = modifier,
        maxLines = maxLines,
        style = style
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TextHeadline4(
            text = "Headline 4"
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewRainbow() {
    ApplicationThemePreview {
        TextHeadline4(
            text = "Headline 4",
            colourType = ColourType.RAINBOW
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewBrand() {
    ApplicationThemePreview {
        TextHeadline4(
            text = "Headline 4",
            colourType = ColourType.BRAND
        )
    }
}