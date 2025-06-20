package tmg.flashback.infrastructure.extensions

import tmg.flashback.infrastructure.utils.DecimalFormatter

fun Double.formatTo(dp: Int): String {
    return DecimalFormatter.format(this, dp)
}

fun Double.roundToHalf(): String {
    val oneDecimalPlace = DecimalFormatter.format(this, 1)
    return if (oneDecimalPlace.endsWith(".5")) {
        return oneDecimalPlace
    } else if (oneDecimalPlace.contains(".") ){
        return oneDecimalPlace.split(".").first()
    } else {
        oneDecimalPlace
    }
}