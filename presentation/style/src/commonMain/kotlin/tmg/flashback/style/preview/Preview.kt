package tmg.flashback.style.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview

@Composable
fun PreviewLightMode(content: @Composable () -> Unit) {
    AppThemePreview(isLight = true) {
        Box(modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary)
        ) {
            content()
        }
    }
}

@Composable
fun PreviewDarkMode(content: @Composable () -> Unit) {

    AppThemePreview(isLight = false) {
        Box(modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary)
        ) {
            content()
        }
    }
}