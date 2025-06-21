package tmg.flashback.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider

fun Modifier.edgeBar(
    colour: Color,
    size: Dp = 6.dp
): Modifier {
    return this
        .drawBehind {
            this.drawRect(
                color = colour,
                size = Size(size.toPx(), this.size.height)
            )
        }
        .padding(start = size)
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Box(modifier = Modifier
            .size(width = 50.dp, height = 16.dp)
            .background(Color.White)
            .edgeBar(AppTheme.colors.primary)
        )
    }
}