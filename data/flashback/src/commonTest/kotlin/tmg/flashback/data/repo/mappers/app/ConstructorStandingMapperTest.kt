package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeConstructorDataMapper
import tmg.flashback.data.repo.fakes.fakeDriverDataMapper
import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingWithDrivers
import tmg.flashback.persistence.flashback.models.standings.model
import tmg.flashback.formula1.model.SeasonConstructorStandingSeason
import tmg.flashback.formula1.model.SeasonConstructorStandings
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class ConstructorStandingMapperTest {

    private lateinit var underTest: ConstructorStandingMapper

    internal fun initUnderTest() {
        underTest = ConstructorStandingMapper(
            driverDataMapper = fakeDriverDataMapper(),
            constructorDataMapper = fakeConstructorDataMapper()
        )
    }

    @Test
    fun `mapConstructorStanding model maps fields correctly`() {
        initUnderTest()

        val input = ConstructorStandingWithDrivers.model()
        val expected = SeasonConstructorStandingSeason.model()

        assertEquals(expected, underTest.mapConstructorStanding(input))
    }

    @Test
    fun `mapConstructorStanding list maps fields correctly`() {
        initUnderTest()

        val input = listOf(ConstructorStandingWithDrivers.model())
        val expected = SeasonConstructorStandings.model()

        assertEquals(expected, underTest.mapConstructorStanding(input))
    }

    @Test
    fun `mapConstructorStanding list returns null if list is empty`() {
        initUnderTest()

        val input = emptyList<ConstructorStandingWithDrivers>()

        assertNull(underTest.mapConstructorStanding(input))
    }

}