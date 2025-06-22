package tmg.flashback.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextBody1

fun Modifier.edgeFade(
    edgeWidth: Dp = AppTheme.dimens.medium,
): Modifier = composed {
    val backgroundColor = AppTheme.colors.surface
    this.drawWithContent {
        this.drawContent()
        this.drawRect(
            brush = Brush.horizontalGradient(0f to backgroundColor, 1f to Color.Transparent, startX = 0f, endX = edgeWidth.toPx()),
            topLeft = Offset(0f, 0f)
        )
        this.drawRect(
            brush = Brush.horizontalGradient(0f to Color.Transparent, 1f to backgroundColor, startX = this.size.width - edgeWidth.toPx(), endX = this.size.width),
            topLeft = Offset(this.size.width - edgeWidth.toPx(), 0f),
        )
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AppThemePreview(isLight = true) {
        Preview()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    AppThemePreview(isLight = false) {
        Preview()
    }
}

@Composable
private fun Preview() {
    Box(
        Modifier.edgeFade()
    ) {
        TextBody1(text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ")
    }
}