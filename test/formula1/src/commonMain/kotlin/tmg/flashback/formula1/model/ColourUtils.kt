package tmg.flashback.formula1.model


fun String.toColourInt(): Int {
    val cleanHexString = when (this.startsWith("#")) {
        true -> this.substring(1)
        false -> this
    }

    val alpha: Int
    val red: Int
    val green: Int
    val blue: Int

    try {
        when (cleanHexString.length) {
            6 -> { // RRGGBB (implicitly opaque)
                alpha = 0xFF // Full opacity
                red = cleanHexString.substring(0, 2).toInt(16)
                green = cleanHexString.substring(2, 4).toInt(16)
                blue = cleanHexString.substring(4, 6).toInt(16)
            }
            8 -> { // AARRGGBB
                alpha = cleanHexString.substring(0, 2).toInt(16)
                red = cleanHexString.substring(2, 4).toInt(16)
                green = cleanHexString.substring(4, 6).toInt(16)
                blue = cleanHexString.substring(6, 8).toInt(16)
            }
            else -> {
                alpha = 0xff
                red = 0x88
                green= 0x88
                blue = 0x88
            }
        }
    } catch (e: NumberFormatException) {
        return 0xFF888888.toInt()
    }

    // Combine into ARGB integer: (A shl 24) or (R shl 16) or (G shl 8) or B
    return (alpha shl 24) or (red shl 16) or (green shl 8) or blue

    val colourLong = cleanHexString.toLongOrNull(16) ?: 0x888888
    return colourLong.toInt()
}