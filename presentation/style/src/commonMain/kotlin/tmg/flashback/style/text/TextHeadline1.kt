package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

@Composable
fun TextHeadline1(
    text: String,
    modifier: Modifier = Modifier,
    brand: Boolean = false
) {
    Text(
        text,
        modifier = modifier,
        style = AppTheme.typography.h1.copy(
            color = when (brand) {
                true -> AppTheme.colors.primary
                false -> AppTheme.colors.onSurface
            }
        ),
    )
}
@Composable
fun TextHeadline1Inline(
    text: String,
    modifier: Modifier = Modifier,
    brand: Boolean = false
) {
    Text(
        text,
        modifier = modifier,
        style = AppTheme.typography.h1.copy(
            color = when (brand) {
                true -> AppTheme.colors.primary
                false -> AppTheme.colors.onSurface
            }
        )
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TextHeadline1(
            text = "Headline 1"
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewBrand() {
    ApplicationThemePreview {
        TextHeadline1(
            text = "Headline 1 Brand",
            brand = true
        )
    }
}