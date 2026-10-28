package tmg.flashback.composeApp.presentation.sync

import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import tmg.flashback.composeApp.repositories.OnboardingRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
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

            val overallReceiver = underTest.overall.testIn(backgroundScope)

            assertEquals(OverallSyncState.InProgress(0f), overallReceiver.awaitItem())

            underTest.startInitialSync()

            assertEquals(OverallSyncState.InProgress(0.2f), overallReceiver.awaitItem())

            advanceUntilIdle()

            assertEquals(OverallSyncState.InProgress(0.4f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.InProgress(0.6f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.InProgress(0.8f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.Successful, overallReceiver.awaitItem())

            verify { mockOnboardingRepository.initialSyncCompleted = true }
        }
    }

    @Test
    fun `start sync returns failed when at least one sync fails`() = runTest(testScheduler) {
        turbineScope {
            initUnderTest()

            everySuspend { mockCircuitRepository.populateCircuits() } returns Response.Unknown
            everySuspend { mockConstructorRepository.populateConstructors() } returns Response.Successful
            everySuspend { mockDriverRepository.populateDrivers() } returns Response.Successful
            everySuspend { mockOverviewRepository.populateOverview() } returns Response.Successful
            every { mockDoesConfigRequireSyncUseCase.invoke() } returns false

            val overallReceiver = underTest.overall.testIn(backgroundScope)

            assertEquals(OverallSyncState.InProgress(0f), overallReceiver.awaitItem())

            underTest.startInitialSync()

            assertEquals(OverallSyncState.InProgress(0.2f), overallReceiver.awaitItem())

            advanceUntilIdle()

            assertEquals(OverallSyncState.InProgress(0.4f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.InProgress(0.6f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.Failed, overallReceiver.awaitItem())
        }
    }

    @Test
    fun `start sync when remote config requires sync`() = runTest(testScheduler) {
        turbineScope {
            initUnderTest()

            everySuspend { mockCircuitRepository.populateCircuits() } returns Response.Successful
            everySuspend { mockConstructorRepository.populateConstructors() } returns Response.Successful
            everySuspend { mockDriverRepository.populateDrivers() } returns Response.Successful
            everySuspend { mockOverviewRepository.populateOverview() } returns Response.Successful
            every { mockDoesConfigRequireSyncUseCase.invoke() } returns true
            everySuspend { mockResetConfigUseCase.ensureReset() } returns true
            everySuspend { mockFetchConfigRepository.fetchAndApply() } returns true

            val overallReceiver = underTest.overall.testIn(backgroundScope)

            assertEquals(OverallSyncState.InProgress(0f), overallReceiver.awaitItem())

            underTest.startInitialSync()

            advanceUntilIdle()

            assertEquals(OverallSyncState.InProgress(0.2f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.InProgress(0.4f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.InProgress(0.6f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.InProgress(0.8f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.Successful, overallReceiver.awaitItem())

            verify { mockOnboardingRepository.initialSyncCompleted = true }
        }
    }

    @Test
    fun `start sync can retry and succeed after failure`() = runTest(testScheduler) {
        turbineScope {
            initUnderTest()

            everySuspend { mockCircuitRepository.populateCircuits() } returns Response.Unknown
            everySuspend { mockConstructorRepository.populateConstructors() } returns Response.Successful
            everySuspend { mockDriverRepository.populateDrivers() } returns Response.Successful
            everySuspend { mockOverviewRepository.populateOverview() } returns Response.Successful
            every { mockDoesConfigRequireSyncUseCase.invoke() } returns false

            val overallReceiver = underTest.overall.testIn(backgroundScope)

            assertEquals(OverallSyncState.InProgress(0f), overallReceiver.awaitItem())

            underTest.startInitialSync()

            assertEquals(OverallSyncState.InProgress(0.2f), overallReceiver.awaitItem())

            advanceUntilIdle()

            assertEquals(OverallSyncState.InProgress(0.4f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.InProgress(0.6f), overallReceiver.awaitItem())
            assertEquals(OverallSyncState.Failed, overallReceiver.awaitItem())

            // Retry with circuit succeeding
            everySuspend { mockCircuitRepository.populateCircuits() } returns Response.Successful

            underTest.startInitialSync()

            assertEquals(OverallSyncState.InProgress(0.8f), overallReceiver.awaitItem())

            advanceUntilIdle()

            assertEquals(OverallSyncState.Successful, overallReceiver.awaitItem())

            verify { mockOnboardingRepository.initialSyncCompleted = true }
        }
    }
}
