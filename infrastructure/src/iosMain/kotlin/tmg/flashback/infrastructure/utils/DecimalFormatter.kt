package tmg.flashback.infrastructure.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

actual object DecimalFormatter {
    actual fun format(double: Double, decimalPlaces: Int): String {
        val formatter = NSNumberFormatter()
        formatter.minimumFractionDigits = 0u
        formatter.maximumFractionDigits = decimalPlaces.toULong()
        formatter.numberStyle = decimalPlaces.toULong() //Decimal
        return formatter.stringFromNumber(NSNumber(double))!!
    }
}