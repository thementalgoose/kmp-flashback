package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeConstructorDataMapper
import tmg.flashback.data.repo.fakes.fakeDriverDataMapper
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.LineupSeason
import tmg.flashback.formula1.model.model
import tmg.flashback.persistence.flashback.models.lineup.LineupWithDrivers
import tmg.flashback.persistence.flashback.models.lineup.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LineupMapperTest {

    private lateinit var sut: LineupMapper

    internal fun initUnderTest() {
        sut = LineupMapper(
            driverDataMapper = fakeDriverDataMapper(),
            constructorDataMapper = fakeConstructorDataMapper(),
        )
    }

    @Test
    fun `mapLineup maps fields correctly`() {
        initUnderTest()

        val inputModel = LineupWithDrivers.model()
        val expected = LineupSeason(
            season = 2020,
            driversToConstructors = mapOf(Driver.model() to Constructor.model())
        )

        assertEquals(listOf(expected), sut.mapLineup(listOf(inputModel)))
    }
}