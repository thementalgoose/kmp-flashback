package tmg.flashback.infrastructure.extensions

/**
 * Accepted formats of `#123456`, `123456`, `#12345678`, `12345678`
 * Invalid format will return a Grey
 */
fun String.toColourInt(): Int {
    val cleanHexString = when (this.startsWith("#")) {
        true -> this.substring(1)
        false -> this
    }
    val colourLong = cleanHexString.toLongOrNull(16) ?: 0x888888
    return colourLong.toInt()
}

inline fun <reified T : Enum<T>> String.toEnum(by: (enum: T) -> String = { it.name }): T? {
    return enumValues<T>().firstOrNull { by(it) == this }
}