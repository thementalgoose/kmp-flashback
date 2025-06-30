package tmg.flashback.feature.season.presentation.calendar

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.EventRepository
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.formula1.model.Overview
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.model
import tmg.flashback.formula1.model.notifications.NotificationSchedule
import tmg.flashback.infrastructure.datetime.now
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class CalendarScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockOverviewRepository: OverviewRepository = mock(autoUnit)
    private val mockRaceRepository: RaceRepository = mock(autoUnit)
    private val mockCurrentSeasonHolder: CurrentSeasonHolder = mock(autoUnit)
    private val mockEventsRepository: EventRepository = mock(autoUnit)
    private val mockCalendarRepository: CalendarRepository = mock(autoUnit)

    private val currentSeasonFlow: MutableStateFlow<Int> = MutableStateFlow(2020)

    private val overview1 = OverviewRace.model(round = 1, date = LocalDate(2020, 1, 1))
    private val overview2 = OverviewRace.model(round = 2, date = LocalDate(2020, 1, 3))
    private val overview3 = OverviewRace.model(round = 3, date = LocalDate(2020, 1, 6))
    private val overview4 = OverviewRace.model(round = 4, date = LocalDate(2020, 1, 9))
    private val overview5 = OverviewRace.model(round = 1, date = LocalDate(2021, 1, 9))
    private val fakeNotificationSchedule = NotificationSchedule(false, false, false, false, false, false)
    private val expectedRaceWeek1 = CalendarItem.RaceWeek(model = overview1, notificationSchedule = fakeNotificationSchedule)
    private val expectedRaceWeek2 = CalendarItem.RaceWeek(model = overview2, notificationSchedule = fakeNotificationSchedule)
    private val expectedRaceWeek3 = CalendarItem.RaceWeek(model = overview3, notificationSchedule = fakeNotificationSchedule)
    private val expectedRaceWeek4 = CalendarItem.RaceWeek(model = overview4, notificationSchedule = fakeNotificationSchedule)
    private val expectedRaceWeek5 = CalendarItem.RaceWeek(model = overview5, notificationSchedule = fakeNotificationSchedule)

    private lateinit var underTest: CalendarScreenViewModel

    private fun initUnderTest() {
        every { mockOverviewRepository.getOverview(2022) } returns flow {
            emit(Overview.model(overviewRaces = listOf(overview5)))
        }

        everySuspend { mockOverviewRepository.populateOverview(any())} returns Response.Successful
        everySuspend { mockRaceRepository.populateRaces(any())} returns Response.Successful
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockEventsRepository.getEvents(any<Int>()) } returns flow { emit(emptyList()) }
        every { mockCurrentSeasonHolder.currentSeason } returns 2019
        every { mockCurrentSeasonHolder.currentSeasonFlow } returns currentSeasonFlow

        underTest = CalendarScreenViewModel(
            overviewRepository = mockOverviewRepository,
            raceRepository = mockRaceRepository,
            currentSeasonHolder = mockCurrentSeasonHolder,
            eventsRepository = mockEventsRepository,
            calendarRepository = mockCalendarRepository,
            mainDispatcher = testDispatcher
        )
    }

    @Test
    fun `initialse viewmodel will load race week data`() = runTest {
        every { mockCurrentSeasonHolder.defaultSeason } returns 2021
        every { mockCalendarRepository.collapseList } returns false
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(overviewRaces = listOf(overview1, overview2)))
        }
        initUnderTest()
        underTest.uiState.test {
            val seasonInState = awaitItem()
            assertEquals(2019, seasonInState.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val newSeason = awaitItem()
            assertEquals(2020, newSeason.season)

            val data = awaitItem()
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2), data.items)
        }
    }

    @Test
    fun `current season changing updates data`() = runTest {
        every { mockCurrentSeasonHolder.defaultSeason } returns 2021
        every { mockCalendarRepository.collapseList } returns false
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(overviewRaces = listOf(overview1, overview2)))
        }
        initUnderTest()
        underTest.uiState.test {
            val seasonInState = awaitItem()
            assertEquals(2019, seasonInState.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val newSeason = awaitItem()
            assertEquals(2020, newSeason.season)

            val data = awaitItem()
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2), data.items)

            currentSeasonFlow.emit(2022)

            testDispatcher.scheduler.advanceUntilIdle()

            // Updating season
            val newData = awaitItem()
            assertEquals(2022, newData.season)
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2), newData.items)

            // Updating data from DB
            val dbData = awaitItem()
            assertEquals(2022, dbData.season)
            assertEquals(listOf(expectedRaceWeek5), dbData.items)
        }
    }


    @Test
    fun `refreshing view model repopulates data`() = runTest {
        every { mockCurrentSeasonHolder.defaultSeason } returns 2021
        every { mockCalendarRepository.collapseList } returns false
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(overviewRaces = listOf(overview1, overview2)))
        }
        initUnderTest()
        underTest.uiState.test {
            val seasonInState = awaitItem()
            assertEquals(2019, seasonInState.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val newSeason = awaitItem()
            assertEquals(2020, newSeason.season)

            val data = awaitItem()
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2), data.items)

            underTest.refresh()

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(true, awaitItem().isLoading)

            assertEquals(false, awaitItem().isLoading)

            verifySuspend {
                mockOverviewRepository.populateOverview(2020)
                mockRaceRepository.populateRaces(2020)
            }
        }
    }

    @Test
    fun `initialse viewmodel will call refresh if current season matches default season`() = runTest {
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(overviewRaces = listOf(overview1, overview2)))
        }
        every { mockCalendarRepository.collapseList } returns false
        every { mockCurrentSeasonHolder.defaultSeason } returns 2020
        initUnderTest()
        underTest.uiState.test {
            val seasonInState = awaitItem()
            assertEquals(2019, seasonInState.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val data = awaitItem()
            assertEquals(emptyList(), data.items)

            testDispatcher.scheduler.advanceUntilIdle()

            awaitItem().also { println(it) }

            verifySuspend {
                mockOverviewRepository.populateOverview(2020)
                mockRaceRepository.populateRaces(2020)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initialse viewmodel will call refresh if no DB values found for given season`() = runTest {
        every { mockCurrentSeasonHolder.defaultSeason } returns 2021
        every { mockCalendarRepository.collapseList } returns false
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(overviewRaces = emptyList()))
        }
        initUnderTest()
        underTest.uiState.test {
            val seasonInState = awaitItem()
            assertEquals(2019, seasonInState.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val newSeason = awaitItem()
            assertEquals(2020, newSeason.season)

            val data = awaitItem()
            assertEquals(null, data.items)

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(true, awaitItem().isLoading)

            verifySuspend {
                mockOverviewRepository.populateOverview(2020)
                mockRaceRepository.populateRaces(2020)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `clicking grouped races expands list`() = runTest {
        every { mockCurrentSeasonHolder.defaultSeason } returns 2021
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(overviewRaces = listOf(
                OverviewRace.model(round = 1, date = LocalDate.now().minus(5, DateTimeUnit.DAY)),
                OverviewRace.model(round = 2, date = LocalDate.now().minus(4, DateTimeUnit.DAY)),
                OverviewRace.model(round = 3, date = LocalDate.now().minus(3, DateTimeUnit.DAY)),
                OverviewRace.model(round = 4, date = LocalDate.now().minus(2, DateTimeUnit.DAY)),
                OverviewRace.model(round = 5, date = LocalDate.now().plus(1, DateTimeUnit.DAY)),
            )))
        }
        every { mockCalendarRepository.collapseList } returns true
        initUnderTest()
        underTest.uiState.test {
            val seasonInState = awaitItem()
            assertEquals(2019, seasonInState.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val newSeason = awaitItem()
            assertEquals(2020, newSeason.season)

            val data = awaitItem()
            assertEquals(true, data.items!!.any { it is CalendarItem.GroupedCompletedRaces })

            underTest.clickGroupedRaces()

            val uncollapsed = awaitItem()
            assertEquals(true, uncollapsed.items!!.none { it is CalendarItem.GroupedCompletedRaces })
        }
    }
}
