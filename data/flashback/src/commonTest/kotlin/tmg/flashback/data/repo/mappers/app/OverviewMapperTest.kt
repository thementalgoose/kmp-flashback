package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeScheduleMapper
import tmg.flashback.persistence.flashback.models.overview.Overview
import tmg.flashback.persistence.flashback.models.overview.OverviewWithCircuit
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull

internal class OverviewMapperTest {

    private lateinit var underTest: OverviewMapper

    internal fun initUnderTest() {
        underTest = OverviewMapper(
            scheduleMapper = fakeScheduleMapper()
        )
    }

    @Test
    fun `mapOverview maps fields correctly`() {
        initUnderTest()
        
        val input = OverviewWithCircuit.model()
        val expected = OverviewRace.model()

        assertEquals(expected, underTest.mapOverview(input))
    }

    @Test
    fun `mapOverview throws error if date is invalid`() {
        initUnderTest()

        val input = OverviewWithCircuit.model(overview = Overview.model(date = "invalid"))

        assertFails {
            underTest.mapOverview(input)
        }
    }

    @Test
    fun `mapOverview returns null time if invalid`() {
        initUnderTest()

        val input = OverviewWithCircuit.model(overview = Overview.model(time = "invalid"))

        assertNull(underTest.mapOverview(input).time)
    }
}