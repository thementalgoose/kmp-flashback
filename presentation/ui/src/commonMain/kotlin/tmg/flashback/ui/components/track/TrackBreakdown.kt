package tmg.flashback.ui.components.track

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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.ui.extensions.px

private const val duration = 1000

@Composable
fun TrackBreakdown(
    trackBreakdownInfo: TrackBreakdownInfo,
    showDrs: Boolean = false,
    showOvertake: Boolean = false,
    modifier: Modifier = Modifier,
    s1Color: Color = AppTheme.colors.f1Sector1,
    s2Color: Color = AppTheme.colors.f1Sector2,
    s3Color: Color = AppTheme.colors.f1Sector3,
    trackColor: Color = Color.Black,
    trackOutlineColor: Color = Color.White,
    overtakeZoneColor: Color = AppTheme.colors.f1DeltaPositive,
    drsZoneColor: Color = AppTheme.colors.f1DeltaNegative
) {
    TrackBreakdown(
        modifier = modifier,
        pathWidth = trackBreakdownInfo.pathWidth,
        pathHeight = trackBreakdownInfo.pathHeight,
        pathTrackWidth = trackBreakdownInfo.pathTrackWidth,
        pathS1 = trackBreakdownInfo.pathS1,
        pathS2 = trackBreakdownInfo.pathS2,
        pathS3 = trackBreakdownInfo.pathS3,
        pathStartLine = trackBreakdownInfo.pathStartLine,
        showDrs = showDrs,
        showOvertake = showOvertake,
        pathOvertakeZones = trackBreakdownInfo.pathOvertakeZones,
        pathDrsZones = trackBreakdownInfo.pathDrsZones,
        s1Color = s1Color,
        s2Color = s2Color,
        s3Color = s3Color,
        trackColor = trackColor,
        trackOutlineColor = trackOutlineColor,
        overtakeZoneColor = overtakeZoneColor,
        drsZoneColor = drsZoneColor
    )
}

@Composable
fun TrackBreakdown(
    pathWidth: Float,
    pathHeight: Float,
    pathTrackWidth: Float,
    pathS1: String,
    pathS2: String,
    pathS3: String,
    modifier: Modifier = Modifier,
    showDrs: Boolean = false,
    showOvertake: Boolean = false,
    pathAspectRatio: Float = pathWidth / pathHeight,
    pathStartLine: String? = null,
    pathOvertakeZones: List<String> = emptyList(),
    pathDrsZones: List<String> = emptyList(),
    s1Color: Color = AppTheme.colors.f1Sector1,
    s2Color: Color = AppTheme.colors.f1Sector2,
    s3Color: Color = AppTheme.colors.f1Sector3,
    trackColor: Color = Color.Black,
    trackOutlineColor: Color = Color.White,
    overtakeZoneColor: Color = AppTheme.colors.f1DeltaPositive,
    drsZoneColor: Color = AppTheme.colors.f1DeltaNegative
) {
    Box(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val maxWidthPx = maxWidth.px.toFloat()
            val scaleX = (maxWidthPx / pathWidth) - 0.01f

            val startLinePath = remember(pathStartLine) {
                pathStartLine?.let { PathParser().parsePathString(it).toPath() }
            }
            val s1Progress = remember { Animatable(0f) }
            val s1FullPath = remember(pathS1) {
                PathParser().parsePathString(pathS1).toPath()
            }
            val s1PathMeasure = remember { PathMeasure() }
            val s1AnimatedPath = remember { Path() }

            val s2Progress = remember { Animatable(0f) }
            val s2FullPath = remember(pathS2) {
                PathParser().parsePathString(pathS2).toPath()
            }
            val s2PathMeasure = remember { PathMeasure() }
            val s2AnimatedPath = remember { Path() }

            val s3Progress = remember { Animatable(0f) }
            val s3FullPath = remember(pathS3) {
                PathParser().parsePathString(pathS3).toPath()
            }
            val s3PathMeasure = remember { PathMeasure() }
            val s3AnimatedPath = remember { Path() }

            val overtakePaths = remember(pathOvertakeZones) {
                pathOvertakeZones.map { PathParser().parsePathString(it).toPath() }
            }
            val drsZonePaths = remember(pathDrsZones) {
                pathDrsZones.map { PathParser().parsePathString(it).toPath() }
            }

            val lineWidthPx = pathTrackWidth
            val trackWidthPx = pathTrackWidth * 2
            val outlineWidthPx = (trackWidthPx + (lineWidthPx / 2f))

            LaunchedEffect(pathS1, pathS2, pathS3) {
                s1Progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = duration, delayMillis = 0)
                )
                s2Progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = duration, delayMillis = 0)
                )
                s3Progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = duration, delayMillis = 0)
                )
            }

            Canvas(
                modifier = Modifier.aspectRatio(pathAspectRatio)
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
                    width = trackWidthPx,
                    cap = StrokeCap.Square
                )
                val trackOutlineStroke = Stroke(
                    width = outlineWidthPx,
                    cap = StrokeCap.Square
                )
                val startLineStroke = Stroke(
                    width = trackWidthPx - (trackWidthPx / 2),
                    cap = StrokeCap.Square
                )
                val startLineOutlineStroke = Stroke(
                    width = outlineWidthPx - (trackWidthPx / 2),
                    cap = StrokeCap.Square
                )
                val lineStroke = Stroke(
                    width = lineWidthPx,
                    cap = StrokeCap.Butt
                )

                scale(
                    scale = scaleX,
                    pivot = Offset(x = 0f, y = 0f)
                ) {
                    // Track Outline Background
                    drawPath(path = s1FullPath, color = trackOutlineColor, style = trackOutlineStroke)
                    drawPath(path = s2FullPath, color = trackOutlineColor, style = trackOutlineStroke)
                    drawPath(path = s3FullPath, color = trackOutlineColor, style = trackOutlineStroke)

                    // Track Background
                    drawPath(path = s1FullPath, color = trackColor, style = trackStroke)
                    drawPath(path = s2FullPath, color = trackColor, style = trackStroke)
                    drawPath(path = s3FullPath, color = trackColor, style = trackStroke)

                    // Animated Track Outlines
                    drawPath(path = s1AnimatedPath, color = s1Color, style = lineStroke)
                    drawPath(path = s2AnimatedPath, color = s2Color, style = lineStroke)
                    drawPath(path = s3AnimatedPath, color = s3Color, style = lineStroke)

                    // Start line
                    if (startLinePath != null) {
                        drawPath(path = startLinePath, color = trackOutlineColor, style = startLineOutlineStroke)
                    }
                    if (startLinePath != null) {
                        drawPath(path = startLinePath, color = trackColor, style = startLineStroke)
                    }

                    // Overtake zones
                    if (showOvertake) {
                        val overtakeZonePathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        for (path in overtakePaths) {
                            drawPath(
                                path = path,
                                color = overtakeZoneColor,
                                style = Stroke(
                                    width = trackWidthPx / 2f,
                                    pathEffect = overtakeZonePathEffect
                                )
                            )
                        }
                    }

                    // DRS zones
                    if (showDrs) {
                        val drsZonePathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        for (path in drsZonePaths) {
                            drawPath(path = path, color = drsZoneColor, style = Stroke(width = trackWidthPx / 2f, pathEffect = drsZonePathEffect))
                        }
                    }
                }
            }
        }
    }
}

@PreviewTheme
@Composable
private fun PreviewZandvoortOvertakeZone() {
    ApplicationThemePreview {
        TrackBreakdown(
            trackBreakdownInfo = trackBreakdownInfoZandvoort
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewAustralia() {
    ApplicationThemePreview {
        TrackBreakdown(
            trackBreakdownInfo = trackBreakdownInfoAustralia
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewHungaryOvertakeZone() {
    ApplicationThemePreview {
        TrackBreakdown(
            showOvertake = true,
            trackBreakdownInfo = trackBreakdownInfoHungaroring
        )
    }
}