package tmg.flashback.formula1.enums

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class SeasonTyresTest {

    @Test
    fun `season tyres have value for current year`() {
        val currentYear = Clock.System.now().toLocalDateTime(TimeZone.UTC).year

        assertNotNull(SeasonTyres.getBySeason(currentYear))
    }

    val seasons = listOf(
        2011,
        2012,
        2013,
        2014,
        2015,
        2016,
        2017,
        2018,
        2019,
        2020,
        2021,
        2022,
        2023,
        2024,
        2025
    )

    @Test
    fun `season tyres have value for historical years`() {
        seasons.forEach { season ->
            assertNotNull(SeasonTyres.getBySeason(season))
        }
    }

    @Test
    fun `all dry compound labels are in an order`() {
        SeasonTyres.entries.forEach { tyres ->
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
            tyres.tyres
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
        SeasonTyres.entries.forEach { tyres ->
            val order = mapOf(
                string.tyre_intermediate to 1,
                string.tyre_wet to 2
            )

            var ref = 0
            tyres.tyres
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