package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import kotlin.math.absoluteValue

private const val alpha = 0.3f

@Composable
fun RelativePosition(
    delta: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clipToBounds()) {
        when {
            delta > 0 -> {
                BackgroundDown()
                Position(
                    delta = delta,
                    color = AppTheme.colors.f1DeltaPositive,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            delta < 0 -> {
                BackgroundUp()
                Position(
                    delta = delta,
                    color = AppTheme.colors.f1DeltaNegative,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                BackgroundNeutral()
                Position(
                    delta = delta,
                    color = AppTheme.colors.f1DeltaNeutral,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

private fun getUpwardPath(x: Float, y: Float, width: Float, height: Float) = Path()
    .apply {
        moveTo(x + (width / 2f), y)
        lineTo(x, y + (height / 2f))
        lineTo(x, y + height)
        lineTo(x + (width / 2f), y + (height / 2f))
        lineTo(x + width, y + height)
        lineTo(x + width, y + (height / 2f))
        close()
    }
@Composable
private fun BackgroundUp() {
    val colour = AppTheme.colors.f1DeltaNegative.copy(alpha = alpha)
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val chevronHeight = this.size.height / 2f
            val spacing = this.size.height / 3f
            val list = List(4) {
                getUpwardPath(0f, -spacing / 2f + (it * spacing), this.size.width, chevronHeight)
            }

            list.forEach {
                drawPath(it, colour)
            }
        }
    )
}

private fun getNeutralPath(x: Float, y: Float, width: Float, height: Float) = Path()
    .apply {
        moveTo(x, y)
        lineTo(x + width, y)
        lineTo(x + width, y + height)
        lineTo(x, y + height)
        close()
    }
@Composable
private fun BackgroundNeutral() {
    val colour = AppTheme.colors.f1DeltaNeutral.copy(alpha = alpha)
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val chevronHeight = this.size.height / 4f
            val spacing = this.size.height / 3f
            val list = List(4) {
                getNeutralPath(0f, -spacing / 2f + (it * spacing), this.size.width, chevronHeight)
            }
            list.forEach {
                drawPath(it, colour)
            }
        }
    )
}

private fun getDownwardPath(x: Float, y: Float, width: Float, height: Float) = Path()
    .apply {
        moveTo(x, y)
        lineTo(x + (width / 2f), y + (height / 2f))
        lineTo(x + width, y)
        lineTo(x + width, y + (height / 2f))
        lineTo(x + (width / 2f), y + height)
        lineTo(x, y + (height / 2f))
        close()
    }
@Composable
private fun BackgroundDown() {
    val colour = AppTheme.colors.f1DeltaPositive.copy(alpha = alpha)
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val chevronHeight = this.size.height / 2f
            val spacing = this.size.height / 3f
            val list = List(4) {
                getDownwardPath(0f, -spacing / 2f + (it * spacing), this.size.width, chevronHeight)
            }

            list.forEach {
                drawPath(it, colour)
            }
        }
    )
}

@Composable
private fun Position(
    delta: Int,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        color = color,
        text = delta.absoluteValue.toString(),
        modifier = modifier,
        maxLines = 1,
        textAlign = TextAlign.Center,
        style = AppTheme.typography.block.copy(
            fontSize = 20.sp
        )
    )
}

@Preview
@Composable
private fun PreviewPositive() {
    ApplicationThemePreview {
        RelativePosition(
            delta = 3,
            modifier = Modifier.size(64.dp, 64.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewNeutral() {
    ApplicationThemePreview {
        RelativePosition(
            delta = 0,
            modifier = Modifier.size(64.dp, 64.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewNegative() {
    ApplicationThemePreview {
        RelativePosition(
            delta = -3,
            modifier = Modifier.size(64.dp, 64.dp)
        )
    }
}