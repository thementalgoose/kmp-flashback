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


inline fun <reified T : Enum<T>> Int.toEnum(by: (enum: T) -> Int = { it.ordinal }): T? {
    return enumValues<T>().firstOrNull { by(it) == this }
}

val Int.ordinalAbbreviation: String
    get() = when {
        this == 11 || this == 12 || this == 13 -> "${this}th"
        this.toString().endsWith("1") -> "${this}st"
        this.toString().endsWith("2") -> "${this}nd"
        this.toString().endsWith("3") -> "${this}rd"
        else -> "${this}th"
    }