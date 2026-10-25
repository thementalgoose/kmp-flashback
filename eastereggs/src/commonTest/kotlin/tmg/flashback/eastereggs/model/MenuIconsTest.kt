package tmg.flashback.eastereggs.model

import kotlinx.datetime.LocalDate
import tmg.flashback.infrastructure.datetime.now
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertTrue

internal class MenuIconsTest {

    @Test
    fun `update required for easter eggs`() {
        val currentYear = LocalDate.now().year
        val typesNeeded = MenuIcons.entries
            .filter {
                it.dateRanges.none { (start, end) ->
                    start.year == currentYear || end.year == currentYear
                }
            }

        assertTrue(typesNeeded.isNotEmpty(), "Menu icons for easter eggs require updated date ranges. Types needed = ${typesNeeded.joinToString { it.key }}")
    }
}