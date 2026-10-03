package tmg.flashback.formula1.enums

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
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

    @ParameterizedTest
    @MethodSource("seasons")
    fun `season tyres have value for historical years`(season: Int) {
        assertNotNull(SeasonTyres.getBySeason(season))
    }

    @ParameterizedTest
    @MethodSource("seasons")
    fun `all dry compound labels are in an order`(season: Int) {
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
                    assertTrue(false, "Tyre order labels are not in the correct order for season $season!")
                }
                ref = orderVal
            }
    }

    @ParameterizedTest
    @MethodSource("seasons")
    fun `all wet compound labels are in an order`(season: Int) {
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
                    assertTrue(false, "Tyre order labels are not in the correct order for season $season!")
                }
                ref = orderVal
            }
    }

    companion object {
        @JvmStatic
        fun seasons(): List<Int> {
            val currYear = Clock.System.now().toLocalDateTime(TimeZone.UTC).year
            return List((currYear + 1) - startingYear) {
                it + startingYear
            }
        }
    }
}