package tmg.flashback.ui.components.progressbar

import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.plurals
import flashback.presentation.localisation.generated.resources.*
import org.jetbrains.compose.resources.pluralStringResource
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.ui.utils.MeasureTextWidth
import tmg.flashback.ui.utils.contrastTextLight
import kotlin.math.roundToInt

@Composable
fun ProgressBar(
    points: Double,
    maxPoints: Double,
    barColor: Color?,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
) {
    val progress = (points / maxPoints).toFloat().coerceIn(0f, 1f)
    ProgressBar(
        modifier = modifier,
        endProgress = progress,
        barColor = barColor ?: AppTheme.colors.primary,
        label = {
            when (it) {
                0f -> "0"
                progress -> points.roundToHalf()
                else -> (it * maxPoints).takeIf { !it.isNaN() }?.roundToInt()?.toString()
                    ?: points.roundToHalf()
            }
        }
    )
}

@Composable
fun ProgressBar(
    endProgress: Float,
    label: (Float) -> String,
    modifier: Modifier = Modifier,
    reverse: Boolean = false,
    initialValue: Float = 0f,
    animationDuration: Int = 400,
    radius: Dp = AppTheme.dimens.radiusSmall,
    textPadding: Dp = AppTheme.dimens.small,
    barColor: Color = AppTheme.colors.primary,
    barOnColor: Color =  when (contrastTextLight(barColor.toArgb(), threshold = 140)) {
        true -> Color.White
        false -> Color.Black
    },
    backgroundColor: Color = AppTheme.colors.surface,
    backgroundOnColor: Color = AppTheme.colors.onSurface,
) {
    val endProgress = if (endProgress.isNaN()) { 0f } else { endProgress }

    val contentDescription = pluralStringResource(resource = plurals.race_points, quantity = endProgress.roundToInt(), endProgress.toDouble().roundToHalf())
    BoxWithConstraints(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp)
            .fillMaxWidth()
            .clearAndSetSemantics {
                this.contentDescription = contentDescription
            }
            .clip(RoundedCornerShape(radius))
    ) {
        val progressState = remember { mutableStateOf(initialValue) }
        val progress = animateFloatAsState(
            visibilityThreshold = 0.0f,
            targetValue = progressState.value.coerceIn(0f, 1f),
            animationSpec = tween(
                durationMillis = animationDuration,
                easing = FastOutSlowInEasing,
                delayMillis = 0
            ), label = ""
        ).value

        Box(
            modifier = Modifier
                .size(
                    width = maxWidth,
                    height = maxHeight
                )
                .background(backgroundColor)
        )

        if (!reverse) {
            Box(modifier = Modifier
                .size(
                    width = maxWidth * progress,
                    height = maxHeight
                )
                .clip(RoundedCornerShape(radius))
                .background(barColor)
            )
            MeasureTextWidth(
                text = label(endProgress.coerceIn(0f, 1f)),
                modifier = Modifier.align(Alignment.CenterStart),
                content = { textWidth ->
                    val onBar = when {
                        maxWidth * progress > ((textPadding * 2) + textWidth) -> true
                        else -> false
                    }
                    Text(
                        text = label(progress),
                        style = AppTheme.typography.title.copy(
                            fontWeight = FontWeight.Bold,
                            color = when (onBar) {
                                true -> barOnColor
                                false -> backgroundOnColor
                            }
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset(
                                x = when (onBar) {
                                    true -> (maxWidth * progress) - (textWidth + textPadding)
                                    false -> (maxWidth * progress) + textPadding
                                }
                            )
                    )
                }
            )
        } else {
            Box(modifier = Modifier
                .size(
                    width = (maxWidth * progress),
                    height = maxHeight
                )
                .offset(x = (maxWidth * (1 - progress)))
                .clip(RoundedCornerShape(radius))
                .background(barColor)
            )
            MeasureTextWidth(
                text = label(endProgress.coerceIn(0f, 1f)),
                modifier = Modifier.align(Alignment.CenterStart),
                content = { textWidth ->
                    val onBar = when {
                        maxWidth * (1 - progress) > ((textPadding * 2) + textWidth) -> true
                        else -> false
                    }
                    Text(
                        text = label(progress),
                        style = AppTheme.typography.title.copy(
                            fontWeight = FontWeight.Bold,
                            color = when (onBar) {
                                true -> backgroundOnColor
                                false -> barOnColor
                            }
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset(
                                x = when (onBar) {
                                    true -> (maxWidth * (1 - progress)) - (textWidth + textPadding)
                                    false -> (maxWidth * (1 - progress)) + textPadding
                                }
                            )
                    )
                }
            )
        }

        LaunchedEffect(endProgress.coerceIn(0f, 1f)) {
            progressState.value = endProgress
        }
    }
}

private val PREVIEW_COLOUR = Color(0xFF00D2BE)

@Preview
@Composable
private fun Preview10() {
    ApplicationThemePreview(isLight = true) {
        Box(modifier = Modifier.size(width = 180.dp, height = 40.dp)) {
            ProgressBar(
                barColor = PREVIEW_COLOUR,
                endProgress = 0.1f,
                initialValue = 0.1f,
                label = { "$it" }
            )
        }
    }
}

@Preview
@Composable
private fun Preview50() {
    ApplicationThemePreview(isLight = true) {
        Box(modifier = Modifier.size(width = 100.dp, height = 50.dp)) {
            ProgressBar(
                barColor = PREVIEW_COLOUR,
                endProgress = 0.5f,
                initialValue = 0.5f,
                label = { "$it" }
            )
        }
    }
}


@Preview
@Composable
private fun Preview95() {
    ApplicationThemePreview(isLight = true) {
        Box(modifier = Modifier.size(width = 100.dp, height = 30.dp)) {
            ProgressBar(
                barColor = PREVIEW_COLOUR,
                endProgress = 0.95f,
                initialValue = 0.95f,
                label = { "$it" }
            )
        }
    }
}

@Preview
@Composable
private fun Preview10Reverse() {
    ApplicationThemePreview(isLight = true) {
        Box(modifier = Modifier.size(width = 180.dp, height = 40.dp)) {
            ProgressBar(
                barColor = PREVIEW_COLOUR,
                reverse = true,
                endProgress = 0.1f,
                initialValue = 0.1f,
                label = { "$it" }
            )
        }
    }
}

@Preview
@Composable
private fun Preview50Reverse() {
    ApplicationThemePreview(isLight = true) {
        Box(modifier = Modifier.size(width = 100.dp, height = 50.dp)) {
            ProgressBar(
                barColor = PREVIEW_COLOUR,
                reverse = true,
                endProgress = 0.5f,
                initialValue = 0.5f,
                label = { "$it" }
            )
        }
    }
}


@Preview
@Composable
private fun Preview95Reverse() {
    ApplicationThemePreview(isLight = true) {
        Box(modifier = Modifier.size(width = 100.dp, height = 30.dp)) {
            ProgressBar(
                barColor = PREVIEW_COLOUR,
                reverse = true,
                endProgress = 0.95f,
                initialValue = 0.95f,
                label = { "$it" }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewContractColor() {
    ApplicationThemePreview(isLight = true) {
        Column(modifier = Modifier.width(100.dp)) {
            listOf(
                PREVIEW_COLOUR,
                Color(0xFF5CC73E), // Stake,
                Color(0xFF1E34C5), // VCARB,
                Color(0xFF0090FF), // Alpine,
                Color(0xFF006F62), // Aston,
                Color(0xFFDC0000), // Ferrari,
                Color(0xFFd9d9d9), // Haas,
                Color(0xFFFF8700), // McLaren,
                Color(0xFF00D2BE), // Mercedes,
                Color(0xFF0600EF), // Red Bull,
                Color(0xFF005AFF), // Williams,
                Color(0xFFF50501), // Audi,
            ).forEach {
                ProgressBar(
                    modifier = Modifier.height(24.dp),
                    barColor = it,
                    endProgress = 0.95f,
                    initialValue = 0.95f,
                    label = { "$it" }
                )
            }
        }
    }
}