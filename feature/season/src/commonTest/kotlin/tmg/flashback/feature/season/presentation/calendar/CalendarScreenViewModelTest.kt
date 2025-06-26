package tmg.flashback.feature.season.presentation.calendar

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.EventRepository
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
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
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(
                overviewRaces = listOf(overview1, overview2)
            ))
        }
        every { mockOverviewRepository.getOverview(2021) } returns flow {
            emit(
                Overview.model(
                    overviewRaces = listOf(overview5)
                )
            )
        }

        everySuspend { mockOverviewRepository.populateOverview(any())} returns Response.Successful
        everySuspend { mockRaceRepository.populateRaces(any())} returns Response.Successful
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockCalendarRepository.collapseList } returns false
        every { mockEventsRepository.getEvents(any<Int>()) } returns flow { emit(emptyList()) }
        every { mockCurrentSeasonHolder.currentSeason } returns 2020
        every { mockCurrentSeasonHolder.defaultSeason } returns 2021
        every { mockCurrentSeasonHolder.currentSeasonFlow } returns currentSeasonFlow

        underTest = CalendarScreenViewModel(
            overviewRepository = mockOverviewRepository,
            raceRepository = mockRaceRepository,
            currentSeasonHolder = mockCurrentSeasonHolder,
            eventsRepository = mockEventsRepository,
            calendarRepository = mockCalendarRepository,
        )
    }

    @Test
    fun `content refreshed when current season flow changes`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2), awaitItem().items)

            currentSeasonFlow.emit(2021)
            val item = awaitItem()
            assertEquals(listOf(expectedRaceWeek5), item.items)
            assertEquals(2021, item.season)
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
    fun `initial load calls refresh if in current year`() = runTest {
        every { mockCurrentSeasonHolder.defaultSeason } returns 2020
        initUnderTest()
        verifySuspend {
            mockRaceRepository.populateRaces(2020)
            mockOverviewRepository.populateOverview(2020)
        }
    }

    @Test
    fun `initial load does not calls refresh if not in current year`() = runTest {
        every { mockCurrentSeasonHolder.defaultSeason } returns 2021
        initUnderTest()
        verifySuspend(exactly(0)) {
            mockRaceRepository.populateRaces(2021)
            mockOverviewRepository.populateOverview(2021)
        }
    }

    @Test
    fun `initial load sets scheduled list of items`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2), awaitItem().items)
        }
    }

    @Test
    fun `initial load with empty overview sets items to null`() = runTest {
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview(
                season = 2020,
                overviewRaces = emptyList()
            ))
        }
        initUnderTest()
        underTest.uiState.test {
            val item = awaitItem()
            assertEquals(emptyList(), item.items)
            assertEquals(false, item.isLoading)
        }
    }

    @Test
    fun `refresh calls refresh`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2), awaitItem().items)

            every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
                Overview.model(
                    overviewRaces = listOf(overview1, overview2, overview3)
                ))
            }
            underTest.refresh()
            verifySuspend {
                mockRaceRepository.populateRaces(2020)
                mockOverviewRepository.populateOverview(2020)
            }
            assertEquals(listOf(expectedRaceWeek1, expectedRaceWeek2, expectedRaceWeek3), awaitItem().items)
        }
    }

    @Test
    fun `clicking grouped races uncollapses races`() = runTest {
        every { mockOverviewRepository.getOverview(2020) } returns flow { emit(
            Overview.model(
                overviewRaces = listOf(
                    OverviewRace.model(round = 1, date = LocalDate.now().minus(5, DateTimeUnit.DAY)),
                    OverviewRace.model(round = 2, date = LocalDate.now().minus(2, DateTimeUnit.DAY)),
                    OverviewRace.model(round = 3, date = LocalDate.now().minus(1, DateTimeUnit.DAY)),
                    OverviewRace.model(round = 4, date = LocalDate.now().plus(5, DateTimeUnit.DAY)),
                )
            ))
        }
        every { mockCalendarRepository.collapseList } returns true
        initUnderTest()
        underTest.uiState.test {
            assertTrue(awaitItem().items?.any { it is CalendarItem.GroupedCompletedRaces } == true)

            val groupedRaces: CalendarItem.GroupedCompletedRaces = CalendarItem.GroupedCompletedRaces(
                OverviewRace.model(), OverviewRace.model())
            underTest.clickItem(groupedRaces)

            testScheduler.advanceUntilIdle()

            assertTrue(awaitItem().items?.none { it is CalendarItem.GroupedCompletedRaces } == true)
        }
    }
}
