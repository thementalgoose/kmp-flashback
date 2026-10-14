package tmg.flashback.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.insets.safeDrawingHorizontalOnly

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    contentInsets: WindowInsets = WindowInsets.safeDrawingHorizontalOnly,
    bottomBar: @Composable () -> Unit = { },
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable BoxScope.(PaddingValues) -> Unit,
) {
    Scaffold(
        contentWindowInsets = contentInsets,
        modifier = modifier,
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
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