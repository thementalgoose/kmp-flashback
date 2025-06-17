package tmg.flashback.formula1.utils

fun Int.extend(digits: Int): String {
    return when (digits < 10) {
        true -> "0$digits"
        false -> "$digits"
    }
}