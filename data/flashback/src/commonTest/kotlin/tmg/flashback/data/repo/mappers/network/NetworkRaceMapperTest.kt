package tmg.flashback.data.repo.mappers.network

import tmg.flashback.flashbackapi.NetworkQualifyingResult
import tmg.flashback.flashbackapi.NetworkRaceResult
import tmg.flashback.flashbackapi.NetworkSprintResult
import tmg.flashback.persistence.flashback.models.race.QualifyingResult
import tmg.flashback.persistence.flashback.models.race.RaceResult
import tmg.flashback.persistence.flashback.models.race.model
import tmg.flashback.flashbackapi.api.models.races.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class NetworkRaceMapperTest {

    private lateinit var underTest: NetworkRaceMapper

    internal fun initUnderTest() {
        underTest = NetworkRaceMapper()
    }

    @Test
    fun `mapRaceResults maps fields correctly`() {
        initUnderTest()

        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkRaceResult.model()
        val expected = RaceResult.model()

        assertEquals(expected, underTest.mapRaceResults(inputSeason, inputRound, inputData))
    }

    @Test
    fun `mapRaceResults null fastest laps is null model`() {
        initUnderTest()

        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkRaceResult.model(fastestLap = null)

        assertNull(underTest.mapRaceResults(inputSeason, inputRound, inputData).fastestLap)
    }

    @Test
    fun `mapQualifyingResults maps fields correctly`() {
        initUnderTest()

        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkQualifyingResult.model()
        val expected = QualifyingResult.model()

        assertEquals(expected, underTest.mapQualifyingResults(inputSeason, inputRound, inputData))
    }

    @Test
    fun `mapSprintResults maps fields correctly`() {
        initUnderTest()

        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkSprintResult.model()
        val expected = tmg.flashback.persistence.flashback.models.race.SprintRaceResult.model()

        assertEquals(expected, underTest.mapSprintRaceResults(inputSeason, inputRound, inputData))
    }
}