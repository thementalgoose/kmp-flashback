package tmg.flashback.infrastructure.utils

actual object DecimalFormatter {
    actual fun format(double: Double, decimalPlaces: Int): String = double.toString()
}
