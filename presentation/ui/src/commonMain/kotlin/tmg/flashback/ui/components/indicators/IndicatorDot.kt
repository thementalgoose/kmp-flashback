package tmg.flashback.ui.components.indicators

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

private val Dp.minSize
    get() = this / 2

@Composable
fun IndicatorDot(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    circleBorderWidth: Dp = 2.dp,
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size.minSize)
                .clip(CircleShape)
                .background(color)
        )

        val transition = rememberInfiniteTransition(label = "indicator")
        val pulse by transition.animateFloat(
            initialValue = size.minSize.value,
            targetValue = size.value,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1000
                    size.value at 1000 using LinearOutSlowInEasing
                }
            )
        )
        Box(
            modifier = Modifier
                .size(pulse.dp)
                .border(circleBorderWidth, color = color, CircleShape)
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        IndicatorDot(
            color = Color.Magenta
        )
    }
}