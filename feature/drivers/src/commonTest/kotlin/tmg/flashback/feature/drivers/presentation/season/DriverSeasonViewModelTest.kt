package tmg.flashback.feature.drivers.presentation.season

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.DriverRepository
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class DriverSeasonViewModelTest {

    private lateinit var underTest: DriverSeasonViewModel

    private val mockDriverRepository: DriverRepository = mock(autoUnit)

    private val fakeDriverId: String = "driverId"

    private fun initUnderTest() {
        everySuspend { mockDriverRepository.populateDriver(fakeDriverId) } returns Response.Successful
        underTest = DriverSeasonViewModel(
            driverRepository = mockDriverRepository
        )
    }

    @Test
    fun `loading season and driver with races returns data`() = runTest {
        every { mockDriverRepository.getDriverOverview(fakeDriverId) } returns flow { emit(DriverHistory.model()) }

        initUnderTest()
        underTest.uiState.test {
            assertEquals(null, awaitItem())

            underTest.load(2020, fakeDriverId)

            val item = awaitItem()
            assertTrue(item is DriverSeasonUiState.Data)
            assertEquals(2020, item.season)
            assertEquals(Driver.model(), item.driver)
            assertEquals(12, item.stats.size)
            assertEquals(1, item.races.size)
        }
    }

    @Test
    fun `loading season and driver calls refresh`() = runTest {
        initUnderTest()
        underTest.loading.test {
            // initialised to false
            assertEquals(false, awaitItem())

            underTest.load(2020, fakeDriverId)
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())

            verifySuspend {
                mockDriverRepository.populateDriver(fakeDriverId)
            }
        }
    }

    @Test
    fun `loading season and driver with no races returns no race data`() = runTest {
        every { mockDriverRepository.getDriverOverview(fakeDriverId) } returns flow { emit(DriverHistory.model(
            standings = emptyList()
        )) }

        initUnderTest()
        underTest.uiState.test {
            assertEquals(null, awaitItem())

            underTest.load(2020, fakeDriverId)

            val item = awaitItem()
            assertTrue(item is DriverSeasonUiState.NoRaces)
            assertEquals(2020, item.season)
            assertEquals(Driver.model(), item.driver)
        }
    }

    @Test
    fun `loading season and driver with null result returns not found`() = runTest {
        every { mockDriverRepository.getDriverOverview(fakeDriverId) } returns flow { emit(null) }

        initUnderTest()
        underTest.uiState.test {
            assertEquals(null, awaitItem())

            underTest.load(2020, fakeDriverId)

            val item = awaitItem()
            assertTrue(item is DriverSeasonUiState.NotFound)
        }
    }
}