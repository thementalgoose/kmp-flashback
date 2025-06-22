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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random
import kotlin.time.Duration.Companion.nanoseconds

private const val snowflakeDensity = 0.01
private val incrementRange = 0.4f..0.8f
private val sizeRange = 5.0f..12.0f
private const val angleSeed = 25.0f
private val angleSeedRange = -angleSeed..angleSeed
private const val angleRange = 0.1f
private const val angleDivisor = 10000.0f
private const val baseSpeedPxAt60Fps = 2

fun Modifier.snow(isEnabled: Boolean): Modifier {
    if (isEnabled) {
        return this.snowfall()
    }
    return this
}

internal fun Modifier.snowfall() = composed {
    var snowflakesState by remember {
        mutableStateOf(SnowflakesState(-1, IntSize(0, 0)))
    }

    LaunchedEffect(Unit) {
        while (this.isActive) {
            withFrameNanos { newTick ->
                val elapsedMillis =
                    (newTick - snowflakesState.tickNanos).nanoseconds.inWholeMilliseconds
                val wasFirstRun = snowflakesState.tickNanos < 0
                snowflakesState.tickNanos = newTick

                if (wasFirstRun) return@withFrameNanos
                for (snowflake in snowflakesState.snowflakes) {
                    snowflake.update(elapsedMillis)
                }
            }
        }
    }

    val darkTheme = isSystemInDarkTheme()

    onSizeChanged { newSize -> snowflakesState = snowflakesState.resize(newSize) }
        .clipToBounds()
        .drawWithContent {
            drawContent()
            snowflakesState.draw(drawContext.canvas, darkTheme)
        }
}

internal fun ClosedRange<Float>.random() =
    Random.nextFloat() * (endInclusive - start) + start

internal fun Float.random() =
    Random.nextFloat() * this

internal fun Int.random() =
    Random.nextInt(this)

internal fun IntSize.randomPosition() =
    Offset(width.random().toFloat(), height.random().toFloat())

internal data class SnowflakesState(
    var tickNanos: Long,
    val snowflakes: List<Snowflake>,
) {

    constructor(tick: Long, canvasSize: IntSize) : this(tick, createSnowflakes(canvasSize))

    fun draw(canvas: Canvas, isDarkMode: Boolean) {
        snowflakes.forEach { it.draw(canvas, isDarkMode) }
    }

    fun resize(newSize: IntSize) = copy(snowflakes = createSnowflakes(newSize))

    companion object {

        private fun createSnowflakes(canvasSize: IntSize): List<Snowflake> {
            val canvasArea = canvasSize.width * canvasSize.height
            val normalizedDensity = snowflakeDensity.coerceIn(0.0..1.0) / 500.0
            val snowflakesCount = (canvasArea * normalizedDensity).roundToInt()

            return List(snowflakesCount) {
                Snowflake(
                    incrementFactor = incrementRange.random(),
                    size = sizeRange.random(),
                    canvasSize = canvasSize,
                    position = canvasSize.randomPosition(),
                    angle = angleSeed.random() / angleSeed * angleRange + (PI / 2.0) - (angleRange / 2.0)
                )
            }
        }
    }
}

private val snowflakePaintDark = Paint().apply {
    isAntiAlias = true
    color = Color.White.copy(alpha = 0.4f)
    style = PaintingStyle.Fill
}
private val snowflakePaintLight = Paint().apply {
    isAntiAlias = true
    color = Color.LightGray.copy(alpha = 0.4f)
    style = PaintingStyle.Fill
}

internal class Snowflake(
    private val incrementFactor: Float,
    private val size: Float,
    private val canvasSize: IntSize,
    position: Offset,
    angle: Double
) {

    private var position by mutableStateOf(position)
    private var angle by mutableStateOf(angle)

    fun update(elapsedMillis: Long) {
        val increment = incrementFactor * baseSpeedPxAt60Fps
        val xDelta = (increment * cos(angle)).toFloat()
        val yDelta = (increment * sin(angle)).toFloat()
        position = Offset(position.x + xDelta, position.y + yDelta)
        angle += angleSeedRange.random() / angleDivisor

        if (position.y > canvasSize.height + size) {
            position = Offset(position.x, -size)
        }
    }

    fun draw(canvas: Canvas, isDarkMode: Boolean) {
        if (isDarkMode) {
            canvas.drawCircle(position, size, snowflakePaintDark)
        } else {
            canvas.drawCircle(position, size, snowflakePaintLight)
        }
    }
}