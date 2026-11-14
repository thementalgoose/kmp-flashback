package tmg.flashback.ui.components.timer

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tmg.flashback.style.AppTheme

@Composable
fun AnimatedHourglassIcon(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    frameColor: Color = AppTheme.colors.onSurfaceVariant,
    sandColor: Color = AppTheme.colors.primary,
    cycleDurationMillis: Int = 4000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "hourglass_transition")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = cycleDurationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hourglass_progress"
    )

    val drainProgress = (progress / 0.75f).coerceIn(0f, 1f)
    val flipFraction = ((progress - 0.75f) / 0.25f).coerceIn(0f, 1f)
    val rotationAngle = FastOutSlowInEasing.transform(flipFraction) * 180f

    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // 50% thinner stroke width (reduced from 0.08f to 0.04f)
        val strokeWidth = w * 0.04f
        // Slightly narrower silhouette (increased horizontal inset from 0.20f to 0.27f)
        val padX = w * 0.27f
        val padY = h * 0.12f

        val topY = padY
        val bottomY = h - padY
        val midY = h / 2f
        val midX = w / 2f
        val leftX = padX
        val rightX = w - padX
        // Scaled neck width slightly to match the narrower frame
        val neckHalfWidth = w * 0.035f

        withTransform({
            rotate(degrees = rotationAngle, pivot = center)
        }) {
            // 1. Interior glass bulb paths for clipping
            val topBulbPath = Path().apply {
                moveTo(leftX, topY)
                lineTo(rightX, topY)
                lineTo(midX + neckHalfWidth, midY)
                lineTo(midX - neckHalfWidth, midY)
                close()
            }

            val bottomBulbPath = Path().apply {
                moveTo(midX - neckHalfWidth, midY)
                lineTo(midX + neckHalfWidth, midY)
                lineTo(rightX, bottomY)
                lineTo(leftX, bottomY)
                close()
            }

            // 2. Top Sand
            clipPath(topBulbPath) {
                val bulbHeight = midY - topY
                val sandTopY = topY + (bulbHeight * drainProgress)
                drawRect(
                    color = sandColor,
                    topLeft = Offset(0f, sandTopY),
                    size = Size(w, midY - sandTopY)
                )
            }

            // 3. Falling Sand Stream (kept proportional to the new thinner stroke)
            if (drainProgress < 1f) {
                val streamStartY = midY
                val streamEndY = bottomY - ((bottomY - midY) * drainProgress)
                drawLine(
                    color = sandColor,
                    start = Offset(midX, streamStartY),
                    end = Offset(midX, streamEndY.coerceAtLeast(midY)),
                    strokeWidth = strokeWidth * 0.8f,
                    cap = StrokeCap.Round
                )
            }

            // 4. Bottom Sand
            clipPath(bottomBulbPath) {
                val bulbHeight = bottomY - midY
                val sandTopY = bottomY - (bulbHeight * drainProgress)
                drawRect(
                    color = sandColor,
                    topLeft = Offset(0f, sandTopY),
                    size = Size(w, bottomY - sandTopY)
                )
            }

            // 5. Glass Outline
            val glassOutline = Path().apply {
                moveTo(leftX, topY)
                lineTo(rightX, topY)
                lineTo(midX + neckHalfWidth, midY)
                lineTo(rightX, bottomY)
                lineTo(leftX, bottomY)
                lineTo(midX - neckHalfWidth, midY)
                close()
            }
            drawPath(
                path = glassOutline,
                color = frameColor,
                style = Stroke(
                    width = strokeWidth,
                    join = StrokeJoin.Round,
                    cap = StrokeCap.Round
                )
            )

            // 6. Top and Bottom Frame Caps
            val capOverhang = w * 0.045f
            drawLine(
                color = frameColor,
                start = Offset(leftX - capOverhang, topY),
                end = Offset(rightX + capOverhang, topY),
                strokeWidth = strokeWidth * 1.25f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = frameColor,
                start = Offset(leftX - capOverhang, bottomY),
                end = Offset(rightX + capOverhang, bottomY),
                strokeWidth = strokeWidth * 1.25f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimatedHourglassPreview() {
    AnimatedHourglassIcon(size = 64.dp)
}