package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeConstructorDataMapper
import tmg.flashback.data.repo.fakes.fakeDriverDataMapper
import tmg.flashback.persistence.flashback.models.standings.DriverStandingWithConstructors
import tmg.flashback.persistence.flashback.models.standings.model
import tmg.flashback.formula1.model.SeasonDriverStandingSeason
import tmg.flashback.formula1.model.SeasonDriverStandings
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class DriverStandingMapperTest {

    private lateinit var underTest: DriverStandingMapper

    internal fun initUnderTest() {
        underTest = DriverStandingMapper(
            driverDataMapper = fakeDriverDataMapper(),
            constructorDataMapper = fakeConstructorDataMapper()
        )
    }

    @Test
    fun `mapDriverStanding model maps fields correctly`() {
        initUnderTest()

        val input = DriverStandingWithConstructors.model()
        val expected = SeasonDriverStandingSeason.model()

        assertEquals(expected, underTest.mapDriverStanding(input))
    }

    @Test
    fun `mapDriverStanding list maps fields correctly`() {
        initUnderTest()

        val input = listOf(DriverStandingWithConstructors.model())
        val expected = SeasonDriverStandings.model()

        assertEquals(expected, underTest.mapDriverStanding(input))
    }

    @Test
    fun `mapDriverStanding list list returns null if list is empty`() {
        initUnderTest()

        val input = emptyList<DriverStandingWithConstructors>()

        assertNull(underTest.mapDriverStanding(input))
    }
}