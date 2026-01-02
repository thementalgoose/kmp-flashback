package tmg.flashback.composeApp.presentation.sync

import app.cash.turbine.test
import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import tmg.flashback.configuration.usecases.DoesConfigRequireSyncUseCase
import tmg.flashback.configuration.usecases.FetchConfigUseCase
import tmg.flashback.configuration.usecases.ResetConfigUseCase
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.data.repo.repository.DriverRepository
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.feature.notifications.usecases.ScheduleResult
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.repositories.OnboardingRepository
import kotlin.coroutines.coroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

internal class SyncViewModelTest {
    private lateinit var underTest: SyncViewModel

    private val mockCircuitRepository: CircuitRepository = mock(autoUnit)
    private val mockConstructorRepository: ConstructorRepository = mock(autoUnit)
    private val mockDriverRepository: DriverRepository = mock(autoUnit)
    private val mockOverviewRepository: OverviewRepository = mock(autoUnit)
    private val mockDoesConfigRequireSyncUseCase: DoesConfigRequireSyncUseCase = mock(autoUnit)
    private val mockResetConfigUseCase: ResetConfigUseCase = mock(autoUnit)
    private val mockScheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase = mock(autoUnit)
    private val mockFetchConfigRepository: FetchConfigUseCase = mock(autoUnit)
    private val mockOnboardingRepository: OnboardingRepository = mock(autoUnit)

    private val testScheduler = TestCoroutineScheduler()

    private fun initUnderTest() {
        everySuspend { mockScheduleUpcomingNotificationsUseCase.invoke(any()) } returns ScheduleResult.Disabled
        underTest = SyncViewModel(
            circuitRepository = mockCircuitRepository,
            constructorRepository = mockConstructorRepository,
            driverRepository = mockDriverRepository,
            overviewRepository = mockOverviewRepository,
            doesConfigRequireSyncUseCase = mockDoesConfigRequireSyncUseCase,
            resetConfigUseCase = mockResetConfigUseCase,
            scheduleUpcomingNotificationsUseCase = mockScheduleUpcomingNotificationsUseCase,
            fetchConfigRepository = mockFetchConfigRepository,
            onboardingRepository = mockOnboardingRepository,
            coroutineContext = testScheduler
        )
    }

    @Test
    fun `start sync returns done when everything is successful`() = runTest(testScheduler) {
        turbineScope {
            initUnderTest()

            everySuspend { mockCircuitRepository.populateCircuits() } returns Response.Successful
            everySuspend { mockConstructorRepository.populateConstructors() } returns Response.Successful
            everySuspend { mockDriverRepository.populateDrivers() } returns Response.Successful
            everySuspend { mockOverviewRepository.populateOverview() } returns Response.Successful
            every { mockDoesConfigRequireSyncUseCase.invoke() } returns false
            everySuspend { mockFetchConfigRepository.fetch() } returns true

            val circuitReceiver = underTest.circuits.testIn(backgroundScope)
            val constructorReceiver = underTest.constructors.testIn(backgroundScope)
            val driverReceiver = underTest.drivers.testIn(backgroundScope)
            val racesReceiver = underTest.races.testIn(backgroundScope)
            val configReceiver = underTest.config.testIn(backgroundScope)
            val overallReceiver = underTest.overall.testIn(backgroundScope)

            assertEquals(SyncState.LOADING, circuitReceiver.awaitItem())
            assertEquals(SyncState.LOADING, constructorReceiver.awaitItem())
            assertEquals(SyncState.LOADING, driverReceiver.awaitItem())
            assertEquals(SyncState.LOADING, racesReceiver.awaitItem())
            assertEquals(SyncState.LOADING, configReceiver.awaitItem())
            assertEquals(SyncState.LOADING, overallReceiver.awaitItem())

            underTest.startInitialSync()

            assertEquals(SyncState.DONE, circuitReceiver.awaitItem())
            assertEquals(SyncState.DONE, constructorReceiver.awaitItem())
            assertEquals(SyncState.DONE, driverReceiver.awaitItem())
            assertEquals(SyncState.DONE, racesReceiver.awaitItem())
            assertEquals(SyncState.DONE, configReceiver.awaitItem())
            assertEquals(SyncState.DONE, overallReceiver.awaitItem())

        }
    }
}