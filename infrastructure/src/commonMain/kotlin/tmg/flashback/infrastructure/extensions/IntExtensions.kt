package tmg.flashback.infrastructure.extensions

fun Int.extend(numberOfDigits: Int, extendWithChar: Char = '0'): String {
    if (this < 0) {
        return this.toString()
    }
    if (this.toString().length >= numberOfDigits) {
        return this.toString()
    }
    val numberToAdd = numberOfDigits - this.toString().length
    return "${numberToAdd.itemsOf { extendWithChar }.joinToString(separator = "")}$this"
}

fun <T> Int.itemsOf(runner: (index: Int) -> T): List<T> {
    return List(this) { runner(it) }
}