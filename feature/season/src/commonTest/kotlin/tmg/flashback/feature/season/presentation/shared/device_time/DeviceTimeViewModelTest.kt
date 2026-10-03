package tmg.flashback.feature.season.presentation.shared.device_time

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.season.repositories.CalendarRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

internal class DeviceTimeViewModelTest {

    private lateinit var underTest: DeviceTimeViewModel

    private val mockCalendarRepository: CalendarRepository = mock(autoUnit)

    private fun initUnderTest(seenDatePrompt: Boolean = false) {
        every { mockCalendarRepository.seenDatePrompt } returns seenDatePrompt

        underTest = DeviceTimeViewModel(
            calendarRepository = mockCalendarRepository
        )
    }

    @Test
    fun `initial uiState show is inverse of seenDatePrompt`() = runTest {
        initUnderTest(seenDatePrompt = false)

        underTest.uiState.test {
            val state = awaitItem()
            assertTrue(state.show)
        }
    }

    @Test
    fun `acknowledge sets seenDatePrompt to true and hides prompt`() = runTest {
        initUnderTest(seenDatePrompt = false)

        underTest.uiState.test {
            assertTrue(awaitItem().show)

            underTest.acknowledge()

            assertFalse(awaitItem().show)
        }

        verify {
            mockCalendarRepository.seenDatePrompt = true
        }
    }
}
