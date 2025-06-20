package tmg.flashback.infrastructure.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

actual object DecimalFormatter {
    actual fun format(double: Double, decimalPlaces: Int): String {
        val df = DecimalFormat()
        df.isGroupingUsed = false
        df.maximumFractionDigits = decimalPlaces
        df.isDecimalSeparatorAlwaysShown = false
        df.decimalFormatSymbols = DecimalFormatSymbols(Locale.ENGLISH)
        return df.format(double)
    }
}