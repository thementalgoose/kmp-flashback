package tmg.flashback.formula1.utils

import java.text.DecimalFormat

internal actual object DecimalFormat {
    actual fun format(double: Double): String {
        val df = DecimalFormat()
        df.isGroupingUsed = false
        df.maximumFractionDigits = 2
        df.isDecimalSeparatorAlwaysShown = false
        return df.format(double)
    }
}