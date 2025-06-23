package tmg.flashback.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.insets.safeDrawingHorizontalOnly

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    contentInsets: WindowInsets = WindowInsets.safeDrawingHorizontalOnly,
    bottomBar: @Composable () -> Unit = { },
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        contentWindowInsets = contentInsets,
        modifier = modifier,
        bottomBar = bottomBar,
        containerColor = AppTheme.colors.surface,
        content = {
            val paddingValues = WindowInsets.safeDrawing
                .exclude(contentInsets)
                .asPaddingValues()
            Box(Modifier
                .fillMaxSize()
                .background(AppTheme.colors.surface)
                .windowInsetsPadding(contentInsets)
                .consumeWindowInsets(contentInsets)
            ) {
                content(paddingValues)
            }
        }
    )
}