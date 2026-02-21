package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme


@Composable
fun TextSection(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    brand: Boolean = false
) {
    Text(
        text,
        modifier = modifier,
        textAlign = textAlign,
        style = AppTheme.typography.section.copy(
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
        TextSection(
            text = "Section"
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewBrand() {
    ApplicationThemePreview {
        TextSection(
            text = "Section Brand",
            brand = true
        )
    }
}