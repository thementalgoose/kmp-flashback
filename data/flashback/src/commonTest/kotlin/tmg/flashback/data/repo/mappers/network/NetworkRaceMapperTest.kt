package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.flashbackapi.NetworkQualifyingResult
import tmg.flashback.flashbackapi.NetworkRaceResult
import tmg.flashback.flashbackapi.NetworkSprintResult
import tmg.flashback.persistence.flashback.models.race.QualifyingResult
import tmg.flashback.persistence.flashback.models.race.RaceResult
import tmg.flashback.persistence.flashback.models.race.model
import tmg.flashback.flashbackapi.api.models.races.model

internal class NetworkRaceMapperTest {

    private lateinit var sut: NetworkRaceMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkRaceMapper()
    }

    @Test
    fun `mapRaceResults maps fields correctly`() {
        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkRaceResult.model()
        val expected = RaceResult.model()

        assertEquals(expected, sut.mapRaceResults(inputSeason, inputRound, inputData))
    }

    @Test
    fun `mapRaceResults null fastest laps is null model`() {
        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkRaceResult.model(fastestLap = null)

        assertNull(sut.mapRaceResults(inputSeason, inputRound, inputData).fastestLap)
    }

    @Test
    fun `mapQualifyingResults maps fields correctly`() {
        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkQualifyingResult.model()
        val expected = QualifyingResult.model()

        assertEquals(expected, sut.mapQualifyingResults(inputSeason, inputRound, inputData))
    }

    @Test
    fun `mapSprintResults maps fields correctly`() {
        val inputSeason = 2020
        val inputRound = 1
        val inputData = NetworkSprintResult.model()
        val expected = tmg.flashback.persistence.flashback.models.race.SprintRaceResult.model()

        assertEquals(expected, sut.mapSprintRaceResults(inputSeason, inputRound, inputData))
    }
}