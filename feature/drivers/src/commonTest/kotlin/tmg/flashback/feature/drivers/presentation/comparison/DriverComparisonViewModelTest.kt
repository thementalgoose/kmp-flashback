package tmg.flashback.feature.drivers.presentation.comparison

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.Season
import tmg.flashback.formula1.model.SeasonDriverStandingSeason
import tmg.flashback.formula1.model.SeasonDriverStandings
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DriverComparisonViewModelTest {

    private lateinit var underTest: DriverComparisonViewModel

    private val mockSeasonRepository: SeasonRepository = mock(autoUnit)
    private val mockRacesRepository: RaceRepository = mock(autoUnit)

    private fun initUnderTest() {
        every { mockSeasonRepository.getSeason(2024) } returns flow { emit(season) }
        every { mockSeasonRepository.getDriverStandings(2024) } returns flow { emit(
            SeasonDriverStandings.model(standings = listOf(
                SeasonDriverStandingSeason.model(season = 2024, driver = driverLeft, points = 10.0),
                SeasonDriverStandingSeason.model(season = 2024, driver = driverRight, points = 20.0)
            ))
        )}

        underTest = DriverComparisonViewModel(
            seasonRepository = mockSeasonRepository,
            racesRepository = mockRacesRepository
        )
    }

    @Test
    fun `setup pulls race list from repo`() = runTest {
        initUnderTest()
        underTest.setup(2024)

        underTest.uiState.test {
            val value = awaitItem()
            assertEquals(listOf(Driver.model(), driverLeft, driverRight), value.driverList)
            assertEquals(false, value.isLoading)
        }
        verify {
            mockSeasonRepository.getSeason(2024)
        }
        verifySuspend {
            mockRacesRepository.populateRaces(2024)
        }
    }

    @Test
    fun `select left driver refreshes state`() = runTest {
        initUnderTest()
        underTest.setup(2024)
        underTest.selectDriverLeft(driverLeft.id)

        underTest.uiState.test {
            val value = awaitItem()
            assertEquals(driverLeft, value.driverLeft)
        }
    }

    @Test
    fun `select right driver refreshes state`() = runTest {
        initUnderTest()
        underTest.setup(2024)
        underTest.selectDriverRight(driverRight.id)

        underTest.uiState.test {
            val value = awaitItem()
            assertEquals(driverRight, value.driverRight)
        }
    }

    @Test
    fun `swapping drivers refreshes state with mirrored data`() = runTest {
        initUnderTest()

        underTest.setup(2024)
        underTest.selectDriverRight(driverRight.id)
        underTest.selectDriverLeft(driverLeft.id)

        advanceUntilIdle()

        underTest.uiState.test {
            awaitItem().let {
                assertEquals(driverRight, it.driverRight)
                assertEquals(driverLeft, it.driverLeft)
            }

            underTest.swapDrivers()

            awaitItem().let {
                assertEquals(driverRight, it.driverLeft)
                assertEquals(driverLeft, it.driverRight)
            }

            underTest.selectDriverLeft(null)

            awaitItem().let {
                assertEquals(null, it.driverLeft)
                assertEquals(driverLeft, it.driverRight)
            }

            underTest.swapDrivers()

            awaitItem().let {
                assertEquals(driverLeft, it.driverLeft)
                assertEquals(null, it.driverRight)
            }

            underTest.selectDriverLeft(null)

            awaitItem().let {
                assertEquals(null, it.driverLeft)
                assertEquals(null, it.driverRight)
            }
        }
    }

    @Test
    fun `setup and selecting drivers populates stats`() = runTest {
        initUnderTest()

        underTest.setup(2024)
        underTest.selectDriverRight(driverRight.id)
        underTest.selectDriverLeft(driverLeft.id)

        advanceUntilIdle()

        underTest.uiState.test {

            val result = awaitItem()

            assertEquals(expectedLeft, result.comparison?.left)
            assertEquals(expectedRight, result.comparison?.right)
            assertEquals(listOf(Constructor.model()), result.comparison?.leftConstructors)
            assertEquals(listOf(Constructor.model()), result.comparison?.rightConstructors)

            underTest.swapDrivers()

            val swappedResult = awaitItem()

            assertEquals(expectedLeft, swappedResult.comparison?.right)
            assertEquals(expectedRight, swappedResult.comparison?.left)
        }
    }

    @Test
    fun `drivers with nothing in common will have no comparison data`() = runTest {
        initUnderTest()

        underTest.setup(2024)
        underTest.selectDriverRight("driverId")
        underTest.selectDriverLeft(driverLeft.id)

        advanceUntilIdle()

        underTest.uiState.test {

            val result = awaitItem()

            assertEquals(null, result.comparison)
        }
    }

    companion object {

        private val driverLeft: Driver = Driver.model(id = "1")
        private val driverRight: Driver = Driver.model(id = "2")
        private val races = listOf(
            Race.model(),
            Race.model(
                raceInfo = RaceInfo.model(round = 1),
                race = listOf(
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverLeft),
                        grid = 3,
                        qualified = 3,
                        finish = 1,
                        status = RaceStatus.FINISHED
                    ),
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverRight),
                        grid = 4,
                        qualified = 7,
                        finish = 3,
                        status = RaceStatus.FINISHED
                    )
                )
            ),
            Race.model(
                raceInfo = RaceInfo.model(round = 2),
                race = listOf(
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverLeft),
                        grid = 7,
                        qualified = 2,
                        finish = 4,
                        status = RaceStatus.FINISHED
                    ),
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverRight),
                        grid = 3,
                        qualified = 4,
                        finish = 2,
                        status = RaceStatus.FINISHED
                    )
                )
            ),
            Race.model(
                raceInfo = RaceInfo.model(round = 3),
                race = listOf(
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverLeft),
                        grid = 9,
                        qualified = 8,
                        finish = 1,
                        status = RaceStatus.FINISHED
                    ),
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverRight),
                        grid = 7,
                        qualified = 1,
                        finish = 11,
                        status = RaceStatus.WITHDREW
                    )
                )
            )
        )
        private val season: Season = Season.model(
            season = 2024,
            races = races
        )


        val expectedLeft = ComparisonValue(
            racesHeadToHead = 2,
            qualifyingHeadToHead = 2,
            points = 10.0,
            pointsFinishes = 3,
            podiums = 2,
            wins = 2,
            dnfs = 0,
        )

        val expectedRight = ComparisonValue(
            racesHeadToHead = 1,
            qualifyingHeadToHead = 1,
            points = 20.0,
            pointsFinishes = 3,
            podiums = 2,
            wins = 0,
            dnfs = 1,
        )
    }
}