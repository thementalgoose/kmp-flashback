package tmg.flashback.style.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider


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

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        TextSection(
            text = "Section"
        )
    }
}

@Preview
@Composable
private fun PreviewBrand(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        TextSection(
            text = "Section Brand",
            brand = true
        )
    }
}