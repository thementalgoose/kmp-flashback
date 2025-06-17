package tmg.flashback.formula1.model

import androidx.compose.ui.graphics.Color

/**
 * Data class for a specific constructor
 */
data class Constructor(
    val id: String,
    val name: String,
    val wikiUrl: String?,
    val photoUrl: String?,
    val nationality: String,
    val nationalityISO: String,
    val color: Int
) {
    companion object

    val colour: Color
        get() = Color(color)
}