package tmg.flashback.style.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme


@Composable
fun TextHeadline4(
    text: String,
    bold: Boolean = false,
    italic: Boolean = false,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    colourType: ColourType = ColourType.DEFAULT
) {
    val style = when (colourType) {
        ColourType.DEFAULT -> AppTheme.typography.h4.copy(
            color = AppTheme.colors.onSurface,
            fontWeight = if (bold) FontWeight.Black else null,
            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
        )
        ColourType.BRAND -> AppTheme.typography.h4.copy(
            color = AppTheme.colors.primary,
            fontWeight = if (bold) FontWeight.Black else null,
            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
        )
        ColourType.RAINBOW -> AppTheme.typography.h4.copy(
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
        TextHeadline3(
            text = "Headline 3"
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewRainbow() {
    ApplicationThemePreview {
        TextHeadline3(
            text = "Headline 3",
            colourType = ColourType.RAINBOW
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewBrand() {
    ApplicationThemePreview {
        TextHeadline3(
            text = "Headline 3",
            colourType = ColourType.BRAND
        )
    }
}