package tmg.flashback.ui.components.timer

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import tmg.flashback.infrastructure.datetime.between
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.plus
import tmg.flashback.infrastructure.datetime.toMillis
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody2
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun Timer(
    targetDateTime: LocalDateTime,
    modifier: Modifier = Modifier,
    countdownContent: @Composable (days: Long, hours: Long, minutes: Long, seconds: Long) -> Unit,
    onFinishedContent: @Composable () -> Unit
) {
    var remainingMillis by remember(targetDateTime) {
        mutableLongStateOf(
            Duration.between(LocalDateTime.now(), targetDateTime)
                .toMillis()
                .coerceAtLeast(0L)
        )
    }

    LaunchedEffect(targetDateTime) {
        while (true) {
            val now = LocalDateTime.now()
            val millis = Duration.between(now, targetDateTime).toMillis()

            if (millis <= 0L) {
                remainingMillis = 0L
                break
            } else {
                remainingMillis = millis
                val nextTickDelay = (millis % 1000L).takeIf { it > 0L } ?: 1000L
                delay(nextTickDelay.milliseconds)
            }
        }
    }

    Crossfade(
        targetState = remainingMillis <= 0L,
        label = "CountdownTransition",
        modifier = modifier
    ) { isFinished ->
        if (isFinished) {
            onFinishedContent()
        } else {
            val totalSeconds = (remainingMillis + 999L) / 1000L
            val days = totalSeconds / 86400L
            val hours = (totalSeconds % 86400L) / 3600L
            val minutes = (totalSeconds % 3600L) / 60L
            val seconds = totalSeconds % 60L

            countdownContent(days, hours, minutes, seconds)
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Timer(
            targetDateTime = LocalDateTime.now()
                .plus(2, DateTimeUnit.SECOND)
                .plus(3, DateTimeUnit.MINUTE)
                .plus(4, DateTimeUnit.HOUR)
                .plus(5, DateTimeUnit.DAY),
            countdownContent = { days, hours, minutes, seconds ->
                TextBody2("Days: $days, Hours: $hours, Minutes: $minutes, Seconds: $seconds")
            },
            onFinishedContent = {
                TextBody2("Finished")
            }
        )
    }
}