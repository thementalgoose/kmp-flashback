package tmg.flashback.ui.components.text

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import tmg.flashback.style.AppTheme

@Composable
fun ShimmerText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    shimmerColor: Color = AppTheme.colors.surfaceContainer5,
    textColor: Color = AppTheme.colors.onSurface,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ShimmeringTextTransition")
    val animationSpec: DurationBasedAnimationSpec<Float> = tween(1000, 4500, LinearEasing)
    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "ShimmerProgress"
    )
    val brush = remember(shimmerProgress) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                // Define the starting X offset, beginning outside the left edge of the text
                val initialXOffset = -size.width
                // Total distance the shimmer will sweep across (double the text width for full coverage)
                val totalSweepDistance = size.width * 2
                // Calculate the current position of the shimmer based on the animation progress
                val currentPosition = initialXOffset + totalSweepDistance * shimmerProgress

                return LinearGradientShader(
                    colors = listOf(textColor, shimmerColor, textColor),
                    from = Offset(currentPosition, 0f),
                    to = Offset(currentPosition + size.width, 0f)
                )
            }
        }
    }
    Text(
        text,
        modifier = modifier,
        maxLines = maxLines,
        style = AppTheme.typography.h2.copy(
            brush = brush
        )
    )
}
