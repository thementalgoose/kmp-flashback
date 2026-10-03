package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeEventMapper
import tmg.flashback.data.repo.fakes.fakeRaceMapper
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.persistence.flashback.models.race.model
import tmg.flashback.formula1.model.Season
import tmg.flashback.formula1.model.model
import tmg.flashback.persistence.flashback.RoomRace
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SeasonMapperTest {

    private lateinit var underTest: SeasonMapper

    internal fun initUnderTest() {
        underTest = SeasonMapper(
            raceMapper = fakeRaceMapper(),
            eventMapper = fakeEventMapper()
        )
    }

    @Test
    fun `mapSeason maps fields correctly`() {
        initUnderTest()

        val inputSeason: Int = 2020
        val inputRaces = listOf(RoomRace.model())
        val inputEvents = listOf(tmg.flashback.persistence.flashback.models.overview.Event.model())
        val expected = Season.model()

        assertEquals(expected, underTest.mapSeason(inputSeason, inputRaces, inputEvents))
    }
}