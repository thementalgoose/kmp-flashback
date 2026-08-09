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
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
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
    pathWidth: Float,
    pathHeight: Float,
    pathTrackWidth: Float,
    pathS1: String,
    pathS2: String,
    pathS3: String,
    modifier: Modifier = Modifier,
    pathAspectRatio: Float = pathWidth / pathHeight,
    pathStartLine: String? = null,
    pathOvertakeZones: List<String> = emptyList(),
    s1Color: Color = AppTheme.colors.f1Sector1,
    s2Color: Color = AppTheme.colors.f1Sector2,
    s3Color: Color = AppTheme.colors.f1Sector3,
    trackColor: Color = Color.Black,
    trackOutlineColor: Color = Color.White
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
                }
            }
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TrackBreakdown(
            pathWidth = 2000f,
            pathHeight = 1100f,
            pathTrackWidth = 50f,
            pathS1 = "M 1090.18 977.382 C 1090.18 977.382 900.362 979.725 813.321 977.584 C 780.522 976.772 784.2 979.108 773.297 945.49 C 766.4 924.235 750.71 904.494 731.486 893.121 C 696.906 872.668 684.167 865.524 612.72 872.57 C 529.282 880.8 444.498 872.113 361.493 860.249 C 311.381 853.074 261.224 840.924 213.805 824.872 C 193.831 818.113 165.21 815.396 157.694 795.698 C 151.249 778.858 176.82 745.103 176.82 745.103 C 176.82 745.103 197.195 715.188 205.946 697.645 C 210.382 688.762 212.926 678.842 209.474 668.077 C 204.726 653.335 178.621 629.171 178.621 629.171 L 86.148 549.391 L 71.83 523.267 C 71.83 523.267 75.476 492.971 77.679 478.374 C 82.963 443.591 88.024 409.726 98.457 376.127 C 115.737 320.45 163.588 213.813 163.588 213.813",
            pathS2 = "M 163.067 213.442 C 163.067 213.442 172.25 194.181 179.201 163.418 C 183.695 143.523 196.034 145.696 207.27 144.327 C 223.955 142.303 250.314 139.163 265.629 133.417 C 290.35 124.16 300.322 110.809 321.531 94.005 C 340.597 78.899 362.386 66.04 385.798 59.432 C 413.005 51.752 452.594 52.079 480.277 57.794 C 514.013 64.76 536.725 84.123 562.889 100.852 C 600.441 124.874 647.904 157.777 677.33 191.264 C 706.628 224.613 735.896 297.556 735.896 297.556 C 735.896 297.556 780.502 411.07 814.461 456.109 C 843.663 494.853 884.857 525.569 924.591 548.18 C 964.831 571.081 1008.5 581.53 1051.98 587.669 C 1089.83 593.012 1164.25 585.079 1164.25 585.079",
            pathS3 = "M 1163.58 584.472 C 1163.58 584.472 1208.3 580.984 1230.1 579.237 C 1247.13 577.874 1262.89 569.56 1274.41 557.898 C 1295.9 536.133 1308.38 520.013 1322.29 503.428 C 1335.11 488.161 1358.03 483.659 1377.79 481.181 C 1414.96 476.527 1453.92 475.881 1491.86 471.911 C 1541.46 466.722 1594.8 464.996 1627.95 472.603 C 1673.82 483.126 1812.3 562.748 1894.64 611.887 C 1913.55 623.169 1931.61 634.739 1949.63 648.48 C 1953.76 651.621 1958.97 655.192 1959.71 660.323 C 1960.43 665.349 1956.34 670.023 1953.48 674.234 C 1937.99 697.217 1921.39 719.086 1907.17 743.13 C 1890.93 770.582 1883.38 792.19 1863.08 828.762 C 1853.84 845.401 1837.64 863.01 1819.46 868.659 C 1798.62 875.139 1775.76 865.952 1754.42 861.475 C 1719.72 854.194 1652.94 829.69 1652.94 829.69 C 1652.94 829.69 1623.9 821.758 1609.31 822.271 C 1604.56 822.442 1600.71 824.967 1598.39 831.396 C 1592.31 848.087 1598.32 890.414 1598.32 890.414 C 1598.32 890.414 1602.72 923.905 1596.42 939.925 C 1592.38 950.253 1587.01 960.699 1575.12 966.904 C 1550.62 979.704 1489.75 978.201 1489.75 978.201 L 1278.2 977.115 L 1089.88 977.018",
            pathStartLine = "M 1090.18 947.382 L 1090.18 1007.382"
        )
    }
}