package tmg.flashback.feature.season.presentation.driver_standings

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.SeasonDriverStandingSeason
import tmg.flashback.formula1.model.SeasonDriverStandings
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DriverStandingsViewModelTest {

    private val mockSeasonRepository: SeasonRepository = mock(autoUnit)
    private val mockCurrentSeasonHolder: CurrentSeasonHolder = mock(autoUnit)
    private val mockOverviewRepository: OverviewRepository = mock(autoUnit)
    private val mockRaceRepository: RaceRepository = mock(autoUnit)

    private lateinit var underTest: DriverStandingsViewModel

    private fun initUnderTest() {
        every { mockSeasonRepository.getDriverStandings(2020) } returns flow { emit(SeasonDriverStandings.model(
            standings = listOf(standing1)
        )) }
        every { mockSeasonRepository.getDriverStandings(2021) } returns flow { emit(SeasonDriverStandings.model(
            standings = listOf(standing2)
        )) }
        every { mockCurrentSeasonHolder.currentSeason } returns 2020
        every { mockCurrentSeasonHolder.currentSeasonFlow } returns mockCurrentSeasonFlow
        underTest = DriverStandingsViewModel(
            seasonRepository = mockSeasonRepository,
            currentSeasonHolder = mockCurrentSeasonHolder,
            overviewRepository = mockOverviewRepository,
            racesRepository = mockRaceRepository,
        )
    }

    private val standing1 = SeasonDriverStandingSeason.model()
    private val standing2 = SeasonDriverStandingSeason.model(driver = Driver.model(id = "2"))
    private val mockCurrentSeasonFlow: MutableStateFlow<Int> = MutableStateFlow(2020)

    @Test
    fun `current season holder emits new season calls populate and refresh`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(2020, awaitItem().season)

            mockCurrentSeasonFlow.emit(2021)

            val item = awaitItem()
            assertEquals(2021, item.season)
            assertEquals(listOf(standing2), item.standings)
        }
    }

    @Test
    fun `initial load sets default season`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(2020, awaitItem().season)
        }
    }

    @Test
    fun `refresh calls fetch`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(listOf(standing1), awaitItem().standings)

            every { mockSeasonRepository.getDriverStandings(2020) } returns flow { emit(SeasonDriverStandings.model(
                standings = listOf(standing1, standing2)
            )) }

            underTest.refresh()
            verifySuspend {
                mockOverviewRepository.populateOverview(2020)
                mockRaceRepository.populateRaces(2020)
            }
            assertEquals(listOf(standing1, standing2), awaitItem().standings)
        }
    }

    @Test
    fun `initial load populates data from repo`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(listOf(standing1), awaitItem().standings)
        }
        verifySuspend(exactly(0)) {
            mockOverviewRepository.populateOverview(any<Int>())
            mockRaceRepository.populateRaces(any<Int>())
        }
    }
}