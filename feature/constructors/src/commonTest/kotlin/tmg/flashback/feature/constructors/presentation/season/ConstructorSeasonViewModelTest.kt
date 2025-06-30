package tmg.flashback.feature.constructors.presentation.season

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
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ConstructorSeasonViewModelTest {

    private lateinit var underTest: ConstructorSeasonViewModel

    private val mockConstructorRepository: ConstructorRepository = mock(autoUnit)

    private val fakeConstructorId: String = "constructorId"

    private fun initUnderTest() {
        everySuspend { mockConstructorRepository.populateConstructor(fakeConstructorId) } returns Response.Successful
        underTest = ConstructorSeasonViewModel(
            constructorRepository = mockConstructorRepository
        )
    }

    @Test
    fun `loading season and constructor with races returns data`() = runTest {
        every { mockConstructorRepository.getConstructorOverview(fakeConstructorId) } returns flow { emit(ConstructorHistory.model()) }

        initUnderTest()
        underTest.uiState.test {
            assertEquals(null, awaitItem())

            underTest.load(2020, fakeConstructorId)

            val item = awaitItem()
            assertTrue(item is ConstructorSeasonUiState.Data)
            assertEquals(2020, item.season)
            assertEquals(Constructor.model(), item.constructor)
            assertEquals(7, item.stats.size)
            assertEquals(1, item.drivers.size)
        }
    }

    @Test
    fun `loading season and constructor calls refresh`() = runTest {
        initUnderTest()
        underTest.loading.test {
            // initialised to false
            assertEquals(false, awaitItem())

            underTest.load(2020, fakeConstructorId)
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())

            verifySuspend {
                mockConstructorRepository.populateConstructor(fakeConstructorId)
            }
        }
    }

    @Test
    fun `loading season and constructor with no races returns no race data`() = runTest {
        every { mockConstructorRepository.getConstructorOverview(fakeConstructorId) } returns flow { emit(ConstructorHistory.model(
            standings = emptyList()
        )) }

        initUnderTest()
        underTest.uiState.test {
            assertEquals(null, awaitItem())

            underTest.load(2020, fakeConstructorId)

            val item = awaitItem()
            assertTrue(item is ConstructorSeasonUiState.NoRaces)
            assertEquals(2020, item.season)
            assertEquals(Constructor.model(), item.constructor)
        }
    }

    @Test
    fun `loading season and constructor with null result returns not found`() = runTest {
        every { mockConstructorRepository.getConstructorOverview(fakeConstructorId) } returns flow { emit(null) }

        initUnderTest()
        underTest.uiState.test {
            assertEquals(null, awaitItem())

            underTest.load(2020, fakeConstructorId)

            val item = awaitItem()
            assertTrue(item is ConstructorSeasonUiState.NotFound)
        }
    }
}