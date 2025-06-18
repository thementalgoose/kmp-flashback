package tmg.flashback.style.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.preview.PreviewDarkMode
import tmg.flashback.style.preview.PreviewLightMode

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
private fun PreviewLight() = PreviewLightMode {
    TextHeadline1(
        text = "Headline 1"
    )
}

@Preview
@Composable
private fun PreviewDark() = PreviewDarkMode {
    TextHeadline1(
        text = "Headline 1"
    )
}

@Preview
@Composable
private fun PreviewBrandLight() = PreviewLightMode {
    TextHeadline1(
        text = "Headline 1 Brand",
        brand = true
    )
}

@Preview
@Composable
private fun PreviewBrandDark() = PreviewDarkMode {
    TextHeadline1(
        text = "Headline 1 Brand",
        brand = true
    )
}