package tmg.flashback.formula1.extensions

import tmg.flashback.formula1.utils.DecimalFormat

/**
 * Print points as a legible string
 */
fun Double.pointsDisplay(): String {
    val oneDecimalPlace = DecimalFormat.format(this)
    return if (oneDecimalPlace.endsWith(".5")) {
        return oneDecimalPlace
    } else if (oneDecimalPlace.contains(".") ){
        return oneDecimalPlace.split(".").first()
    } else {
        oneDecimalPlace
    }
}