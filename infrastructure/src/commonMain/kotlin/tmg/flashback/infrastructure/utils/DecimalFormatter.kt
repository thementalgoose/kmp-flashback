package tmg.flashback.infrastructure.utils

expect object DecimalFormatter {
    fun format(double: Double, decimalPlaces: Int = 1): String
}