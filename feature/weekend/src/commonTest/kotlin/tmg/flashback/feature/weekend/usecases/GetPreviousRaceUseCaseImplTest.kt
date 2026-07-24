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
        val previousRace = OverviewRace.model(season = 2023, round = 10, raceName = "British Grand Prix", circuitId = "silverstone")

        every { mockOverviewRepository.getOverview(any<Int>()) } returns flowOf(Overview(0, overviewRaces = emptyList()))
        every { mockOverviewRepository.getOverview(2023) } returns flowOf(Overview(2023, overviewRaces = listOf(previousRace)))
        every { mockOverviewRepository.getOverview(2022) } returns flowOf(Overview(2022, overviewRaces = emptyList()))
        everySuspend { mockOverviewRepository.getOverview(any<String>()) } returns listOf(previousRace)

        initUnderTest()

        val result = underTest(2024, 1, "silverstone")

        assertEquals(previousRace, result)
    }

    @Test
    fun `returns previous race from the same circuit when no same-name race exists`() = runTest {
        val previousRace = OverviewRace.model(season = 2023, round = 5, raceName = "Monaco Grand Prix", circuitId = "barcelona")

        every { mockOverviewRepository.getOverview(any<Int>()) } returns flowOf(Overview(0, overviewRaces = emptyList()))
        every { mockOverviewRepository.getOverview(2023) } returns flowOf(Overview(2023, overviewRaces = emptyList()))
        every { mockOverviewRepository.getOverview(2022) } returns flowOf(Overview(2022, overviewRaces = emptyList()))
        everySuspend { mockOverviewRepository.getOverview(any<String>()) } returns listOf(previousRace)

        initUnderTest()

        val result = underTest(2024, 1, "barcelona")

        assertEquals(previousRace, result)
    }

    @Test
    fun `returns null when no previous matching race exists`() = runTest {
        every { mockOverviewRepository.getOverview(any<Int>()) } returns flowOf(Overview(0, overviewRaces = emptyList()))
        every { mockOverviewRepository.getOverview(2023) } returns flowOf(Overview(2023, overviewRaces = emptyList()))
        every { mockOverviewRepository.getOverview(2022) } returns flowOf(Overview(2022, overviewRaces = emptyList()))
        every { mockOverviewRepository.getOverview(2021) } returns flowOf(Overview(2021, overviewRaces = emptyList()))
        every { mockOverviewRepository.getOverview(2020) } returns flowOf(Overview(2020, overviewRaces = emptyList()))
        everySuspend { mockOverviewRepository.getOverview(any<String>()) } returns emptyList()

        initUnderTest()

        val result = underTest(2024, 1, "barcelona")

        assertNull(result)
    }
}
