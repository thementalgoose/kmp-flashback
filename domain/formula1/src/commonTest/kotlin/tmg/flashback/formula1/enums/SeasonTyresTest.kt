package tmg.flashback.formula1.enums

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private const val startingYear = 2011

internal class SeasonTyresTest {

    private val currentYear: Int
        get() = Clock.System.now().toLocalDateTime(TimeZone.UTC).year

    @Test
    fun `season tyres have value for current year`() {
        assertNotNull(SeasonTyres.getBySeason(currentYear))
    }

    private val seasons: List<Int>
        get() = List((currentYear + 1) - startingYear) {
            it + startingYear
        }

    @Test
    fun `season tyres have value for historical years`() {
        seasons.forEach { season ->
            assertNotNull(SeasonTyres.getBySeason(season))
        }
    }

    @Test
    fun `all dry compound labels are in an order`() {
        seasons.forEach { season ->
            val order = mapOf(
                string.tyre_hyper_soft to 1,
                string.tyre_ultra_soft to 2,
                string.tyre_super_soft to 3,
                string.tyre_soft to 4,
                string.tyre_medium to 5,
                string.tyre_hard to 6,
                string.tyre_super_hard to 7
            )

            var ref = 0
            SeasonTyres.getBySeason(season)!!
                .tyres
                .filter { it.tyre.isDry }
                .forEach { list ->
                    val orderVal = order[list.label]!!
                    if (orderVal <= ref) {
                        assertTrue(false, "Tyre order labels are not in the correct order!")
                    }
                    ref = orderVal
                }
        }
    }

    @Test
    fun `all wet compound labels are in an order`() {
        seasons.forEach { season ->
            val order = mapOf(
                string.tyre_intermediate to 1,
                string.tyre_wet to 2
            )

            var ref = 0
            SeasonTyres.getBySeason(season)!!
                .tyres
                .filter { !it.tyre.isDry }
                .forEach { list ->
                    val orderVal = order[list.label]!!
                    if (orderVal <= ref) {
                        assertTrue(false, "Tyre order labels are not in the correct order!")
                    }
                    ref = orderVal
                }
        }
    }
}