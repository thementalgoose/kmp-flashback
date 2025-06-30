package tmg.flashback.eastereggs.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.time.Duration.Companion.nanoseconds

private const val baseDegreesRotationIn1s = 1.8f

fun Modifier.summer(isEnabled: Boolean): Modifier {
    if (isEnabled) {
        return this.summer()
    }
    return this
}

internal fun Modifier.summer() = composed {
    var sunrayState by remember {
        mutableStateOf(SunrayState(-1, IntSize(0, 0)))
    }

    LaunchedEffect(Unit) {
        while (this.isActive) {
            withFrameNanos { newTick ->
                val elapsedMillis =
                    (newTick - sunrayState.tickNanos).nanoseconds.inWholeMilliseconds
                val wasFirstRun = sunrayState.tickNanos < 0
                sunrayState.tickNanos = newTick

                if (wasFirstRun) return@withFrameNanos
                sunrayState.update(elapsedMillis)
            }
        }
    }

    val darkTheme = isSystemInDarkTheme()

    onSizeChanged { newSize -> sunrayState = sunrayState.resize(newSize) }
        .clipToBounds()
        .drawWithContent {
            drawContent()
            sunrayState.draw(drawContext.canvas, darkTheme)
        }
}

private val sunrayDarkPaint = Paint().apply {
    isAntiAlias = true
    color = Color(0xFFFCBA03).copy(alpha = 0.015f)
    style = PaintingStyle.Fill
}
private val sunrayLightPaint = Paint().apply {
    isAntiAlias = true
    color = Color(0xFFFFBE45).copy(alpha = 0.03f)
    style = PaintingStyle.Fill
}

internal data class SunrayState(
    var tickNanos: Long,
    val rays: List<Sunray>
) {

    constructor(tick: Long, canvasSize: IntSize): this(tick, createSunrays(canvasSize))

    private val angle = mutableStateOf(0f)

    fun draw(canvas: Canvas, isDarkMode: Boolean) {
        canvas.save()
        canvas.rotate(angle.value)
        rays.forEach { it.draw(canvas, isDarkMode) }
        canvas.restore()
    }

    fun resize(newSize: IntSize) =
        copy(rays = createSunrays(newSize))

    fun update(elapsedMillis: Long) {
        val degreesToMove = baseDegreesRotationIn1s.toFloat() * (elapsedMillis / 1000f)
        this.angle.value -= degreesToMove
    }

    companion object {
        private const val segments = 54
        private fun createSunrays(canvasSize: IntSize): List<Sunray> {
            return List(segments) {
                Sunray(canvasSize, it * (360f / segments.toFloat()), 1.3f)
            }
        }
    }
}

internal class Sunray(
    canvasSize: IntSize,
    angle: Float,
    degreeRange: Float
) {

    private val midX = 10f // sunOriginPercentage * canvasSize.width
    private val midY = 10f // -(canvasSize.height / 5f)

    private val lineLength = (max(canvasSize.width, canvasSize.height) * 1.05f) * abs(midY)

    private fun Float.degreesToRadians() =
        (this * (PI / 180f)).toFloat()

    private val path = Path().apply {
        this.moveTo(midX, midY)
        val angleLower = angle - degreeRange
        val angleUpper = angle + degreeRange
        this.lineTo(
            midX + (sin(angleLower.degreesToRadians()) * lineLength),
            midY + (cos(angleLower.degreesToRadians()) * lineLength)
        )
        this.lineTo(
            midX + (sin(angleUpper.degreesToRadians()) * lineLength),
            midY + (cos(angleUpper.degreesToRadians()) * lineLength)
        )
        this.lineTo(midX, midY)
    }

    fun draw(canvas: Canvas, isDarkMode: Boolean) {
        when (isDarkMode) {
            true -> draw(canvas, sunrayDarkPaint)
            false -> draw(canvas, sunrayLightPaint)
        }
    }

    private fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawPath(path, paint)
    }
}