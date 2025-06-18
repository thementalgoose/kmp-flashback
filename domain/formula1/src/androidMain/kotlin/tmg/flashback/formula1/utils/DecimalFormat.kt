package tmg.flashback.formula1.utils

internal actual object DecimalFormat {
    actual fun format(double: Double): String {
        val df = java.text.DecimalFormat()
        df.isGroupingUsed = false
        df.maximumFractionDigits = 1
        df.isDecimalSeparatorAlwaysShown = false
        return df.format(double)
    }
}