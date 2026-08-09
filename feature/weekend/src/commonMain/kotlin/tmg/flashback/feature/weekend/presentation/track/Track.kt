package tmg.flashback.feature.weekend.presentation.track

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tmg.flashback.formula1.enums.TrackBreakdowns
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.ui.extensions.px

private val trackWidth: Dp = 12.dp
private val outlineWidth: Dp = 20.dp

@Composable
fun Track(
    trackBreakdown: TrackBreakdowns,
    s1: Color = Color.Red,
    s2: Color = Color.Cyan,
    s3: Color = Color.Yellow,
    track: Color = AppTheme.colors.surfaceInverse,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val maxWidthPx = maxWidth.px.toFloat()
            val scaleX = (maxWidthPx / trackBreakdown.pathWidth) - 0.01f

            val startLinePath = remember(trackBreakdown.startLine) {
                trackBreakdown.startLine?.let { PathParser().parsePathString(it).toPath() }
            }
            val s1Progress = remember { Animatable(0f) }
            val s1FullPath = remember(trackBreakdown.s1) {
                PathParser().parsePathString(trackBreakdown.s1).toPath()
            }
            val s1PathMeasure = remember { PathMeasure() }
            val s1AnimatedPath = remember { Path() }

            val s2Progress = remember { Animatable(0f) }
            val s2FullPath = remember(trackBreakdown.s2) {
                PathParser().parsePathString(trackBreakdown.s2).toPath()
            }
            val s2PathMeasure = remember { PathMeasure() }
            val s2AnimatedPath = remember { Path() }

            val s3Progress = remember { Animatable(0f) }
            val s3FullPath = remember(trackBreakdown.s3) {
                PathParser().parsePathString(trackBreakdown.s3).toPath()
            }
            val s3PathMeasure = remember { PathMeasure() }
            val s3AnimatedPath = remember { Path() }

            val trackWidthPx = trackWidth.px.toFloat()
            val outlineWidthPx = outlineWidth.px.toFloat()

            LaunchedEffect(trackBreakdown) {
                s1Progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1500, delayMillis = 0)
                )
                s2Progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1500, delayMillis = 0)
                )
                s3Progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1500, delayMillis = 0)
                )
            }

            Canvas(
                modifier = Modifier.aspectRatio(trackBreakdown.aspectRatio)
            ) {
                s1AnimatedPath.reset()
                s2AnimatedPath.reset()
                s3AnimatedPath.reset()

                s1PathMeasure.setPath(s1FullPath, forceClosed = false)
                s2PathMeasure.setPath(s2FullPath, forceClosed = false)
                s3PathMeasure.setPath(s3FullPath, forceClosed = false)

                s1PathMeasure.getSegment(
                    startDistance = 0f,
                    stopDistance = s1PathMeasure.length * s1Progress.value,
                    destination = s1AnimatedPath,
                    startWithMoveTo = true
                )
                s2PathMeasure.getSegment(
                    startDistance = 0f,
                    stopDistance = s2PathMeasure.length * s2Progress.value,
                    destination = s2AnimatedPath,
                    startWithMoveTo = true
                )
                s3PathMeasure.getSegment(
                    startDistance = 0f,
                    stopDistance = s3PathMeasure.length * s3Progress.value,
                    destination = s3AnimatedPath,
                    startWithMoveTo = true
                )

                val trackStroke = Stroke(
                    width = outlineWidthPx,
                    cap = StrokeCap.Square
                )
                val startLineStroke = Stroke(
                    width = trackWidthPx,
                    cap = StrokeCap.Square
                )

                scale(
                    scale = scaleX,
                    pivot = Offset(x = 0f, y = 0f)
                ) {
                    drawPath(
                        path = s1FullPath,
                        color = track,
                        style = trackStroke
                    )
                    drawPath(
                        path = s2FullPath,
                        color = track,
                        style = trackStroke
                    )
                    drawPath(
                        path = s3FullPath,
                        color = track,
                        style = trackStroke
                    )

                    if (startLinePath != null) {
                        drawPath(
                            path = startLinePath,
                            color = track,
                            style = startLineStroke
                        )
                    }
                    // Render the partial path to the screen
                    drawPath(
                        path = s1AnimatedPath,
                        color = s1,
                        style = Stroke(
                            width = trackWidthPx,
                            cap = StrokeCap.Butt
                        )
                    )
                    drawPath(
                        path = s2AnimatedPath,
                        color = s2,
                        style = Stroke(
                            width = trackWidthPx,
                            cap = StrokeCap.Butt
                        )
                    )
                    drawPath(
                        path = s3AnimatedPath,
                        color = s3,
                        style = Stroke(
                            width = trackWidthPx,
                            cap = StrokeCap.Butt
                        )
                    )
                }
            }
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Track(
            trackBreakdown = TrackBreakdowns.ALBERT_PARK
        )
    }
}