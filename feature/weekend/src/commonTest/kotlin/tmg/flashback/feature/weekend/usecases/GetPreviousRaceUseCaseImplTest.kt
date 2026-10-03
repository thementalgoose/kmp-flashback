package tmg.flashback.feature.weekend.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.formula1.model.Overview
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class GetPreviousRaceUseCaseImplTest {

    private val mockOverviewRepository: OverviewRepository = mock(autoUnit)

    private lateinit var underTest: GetPreviousRaceUseCaseImpl

    private fun initUnderTest() {
        underTest = GetPreviousRaceUseCaseImpl(
            overviewRepository = mockOverviewRepository
        )
    }

    @Test
    fun `returns previous race when the same race name exists in an earlier season`() = runTest {
        val currentRace = OverviewRace.model(season = 2024, round = 1, raceName = "British Grand Prix", circuitId = "silverstone")
        val previousRace = OverviewRace.model(season = 2023, round = 10, raceName = "British Grand Prix", circuitId = "silverstone")

        every { mockOverviewRepository.getOverview(any<Int>(), any<Int>()) } returns flowOf(currentRace)
        everySuspend { mockOverviewRepository.getOverview("silverstone") } returns listOf(previousRace)

        initUnderTest()

        val result = underTest(2024, 1)

        assertEquals(previousRace, result)
    }

    @Test
    fun `returns previous race from the same circuit when no same-name race exists`() = runTest {
        val currentRace = OverviewRace.model(season = 2024, round = 1, raceName = "Spanish Grand Prix", circuitId = "barcelona")
        val previousRace = OverviewRace.model(season = 2023, round = 5, raceName = "Monaco Grand Prix", circuitId = "barcelona")

        every { mockOverviewRepository.getOverview(any<Int>(), any<Int>()) } returns flowOf(currentRace)
        everySuspend { mockOverviewRepository.getOverview("barcelona") } returns listOf(previousRace)

        initUnderTest()

        val result = underTest(2024, 1)

        assertEquals(previousRace, result)
    }

    @Test
    fun `returns null when no previous matching race exists`() = runTest {
        val currentRace = OverviewRace.model(season = 2024, round = 1, raceName = "Spanish Grand Prix", circuitId = "barcelona")

        every { mockOverviewRepository.getOverview(any<Int>(), any<Int>()) } returns flowOf(currentRace)
        everySuspend { mockOverviewRepository.getOverview("barcelona") } returns emptyList()

        initUnderTest()

        val result = underTest(2024, 1)

        assertNull(result)
    }
}
