package tmg.flashback.formula1.extensions

import tmg.flashback.formula1.utils.DecimalFormat

/**
 * Print points as a legible string
 */
fun Double.pointsDisplay(): String {
    return DecimalFormat.format(this)
}