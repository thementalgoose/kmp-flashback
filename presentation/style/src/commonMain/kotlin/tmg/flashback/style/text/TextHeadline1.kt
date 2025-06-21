package tmg.flashback.style.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider

@Composable
fun TextHeadline1(
    text: String,
    modifier: Modifier = Modifier,
    brand: Boolean = false
) {
    Text(
        text,
        modifier = modifier.fillMaxWidth(),
        style = AppTheme.typography.h1.copy(
            color = when (brand) {
                true -> AppTheme.colors.primary
                false -> AppTheme.colors.contentPrimary
            }
        )
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
                false -> AppTheme.colors.contentPrimary
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
        TextHeadline1(
            text = "Headline 1"
        )
    }
}

@Preview
@Composable
private fun PreviewBrand(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        TextHeadline1(
            text = "Headline 1 Brand",
            brand = true
        )
    }
}