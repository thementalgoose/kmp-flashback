package tmg.flashback.style.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview

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