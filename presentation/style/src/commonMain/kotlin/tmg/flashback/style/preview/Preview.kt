package tmg.flashback.style.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.SupportedTheme

data class PreviewConfig(
    val theme: SupportedTheme,
    val isLightMode: Boolean
)

class PreviewConfigProvider: PreviewParameterProvider<PreviewConfig> {
    override val values: Sequence<PreviewConfig> = sequenceOf(
        PreviewConfig(theme = SupportedTheme.Default, isLightMode = true),
        PreviewConfig(theme = SupportedTheme.Default, isLightMode = false)
    )
}

@Composable
internal fun PreviewLightMode(content: @Composable () -> Unit) {
    AppThemePreview(isLight = true) {
        Box(modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary)
        ) {
            content()
        }
    }
}

@Composable
internal fun PreviewDarkMode(content: @Composable () -> Unit) {
    AppThemePreview(isLight = false) {
        Box(modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary)
        ) {
            content()
        }
    }
}