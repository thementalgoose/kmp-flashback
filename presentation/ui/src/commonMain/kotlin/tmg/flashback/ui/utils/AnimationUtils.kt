package tmg.flashback.ui.utils

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing

class SteppedEasing(
    private val steps: Int,
    private val innerEasing: Easing = FastOutSlowInEasing
) : Easing {
    override fun transform(fraction: Float): Float {
        if (steps <= 1) return innerEasing.transform(fraction.coerceIn(0f, 1f))
        if (fraction <= 0f) return 0f
        if (fraction >= 1f) return 1f

        val stepSize = 1f / steps

        // Find current step index (0 to steps - 1)
        val currentStep = (fraction * steps).toInt().coerceIn(0, steps - 1)

        // Normalize progress inside the current step to a 0.0 - 1.0 range
        val localFraction = (fraction - currentStep * stepSize) / stepSize

        // Apply ease-in/ease-out to the local step progress
        val easedLocal = innerEasing.transform(localFraction)

        // Map back to total output range
        return (currentStep + easedLocal) * stepSize
    }
}