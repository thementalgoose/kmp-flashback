package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

@Composable
fun TextHeadline2(
    text: String,
    bold: Boolean = false,
    italic: Boolean = false,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    colourType: ColourType = ColourType.DEFAULT
) {
    val style = when (colourType) {
        ColourType.DEFAULT -> AppTheme.typography.h2.copy(
            color = AppTheme.colors.onSurface,
            fontWeight = if (bold) FontWeight.Black else null,
            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
        )
        ColourType.BRAND -> AppTheme.typography.h2.copy(
            color = AppTheme.colors.primary,
            fontWeight = if (bold) FontWeight.Black else null,
            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
        )
        ColourType.RAINBOW -> AppTheme.typography.h2.copy(
            brush = Brush.horizontalGradient(RainbowColors),
            fontWeight = if (bold) FontWeight.Black else null,
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
        TextHeadline2(
            text = "Headline 2"
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewBold() {
    ApplicationThemePreview {
        TextHeadline2(
            text = "Headline 2",
            bold = true
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewRainbow() {
    ApplicationThemePreview {
        TextHeadline2(
            text = "Headline 2",
            colourType = ColourType.RAINBOW
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewBrand() {
    ApplicationThemePreview {
        TextHeadline2(
            text = "Headline 2",
            colourType = ColourType.BRAND
        )
    }
}