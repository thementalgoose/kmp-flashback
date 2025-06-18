package tmg.flashback.formula1.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

internal actual object DecimalFormat {
    actual fun format(double: Double): String {
        val formatter = NSNumberFormatter()
        formatter.minimumFractionDigits = 0u
        formatter.maximumFractionDigits = 1u
        formatter.numberStyle = 1u //Decimal
        return formatter.stringFromNumber(NSNumber(double))!!
    }
}