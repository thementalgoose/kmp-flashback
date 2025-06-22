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
        val minYear = MenuIcons.entries
            .map { min(it.start.year, it.end.year) }
            .minBy { it }

        assertTrue(minYear <= currentYear, "Menu icons for easter eggs require updated date ranges, minYear found = $minYear, currentYear = $currentYear")
    }
}