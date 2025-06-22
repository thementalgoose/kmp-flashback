package tmg.flashback.eastereggs.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.datetime.LocalDateTime
import tmg.flashback.eastereggs.repository.FakeEasterEggsRepository
import tmg.flashback.infrastructure.datetime.TimeManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class IsSnowEnabledUseCaseTest {

    private val fakeEasterEggsRepository = FakeEasterEggsRepository()
    private val mockTimeManager: TimeManager = mock(autoUnit)

    private lateinit var underTest: IsSnowEnabledUseCaseImpl

    private fun initUnderTest() {
        underTest = IsSnowEnabledUseCaseImpl(
            easterEggsRepository = fakeEasterEggsRepository,
            timeManager = mockTimeManager
        )
        every { mockTimeManager.now } returns LocalDateTime(2023, 6, 6, 0, 0)
    }

    @Test
    fun `snow is disabled if config value is disabled`() {
        fakeEasterEggsRepository.isSnowEnabled = false

        initUnderTest()
        assertFalse(underTest.invoke())
    }

    @Test
    fun `snow is enabled if config value is enabled`() {
        fakeEasterEggsRepository.isSnowEnabled = true

        initUnderTest()
        assertTrue(underTest.invoke())
    }

    private data class TestCase(
        val inputMonth: Int,
        val inputDay: Int,
        val expectedState: Boolean
    )

    private val testCases = listOf(
        TestCase(11, 16 ,false),
        TestCase(12, 19 ,false),
        TestCase(12, 20 ,true),
        TestCase(12, 25 ,true),
        TestCase(12, 31 ,true),
        TestCase(1, 1 ,true),
        TestCase(1, 6 ,true),
        TestCase(1, 14 ,true),
        TestCase(1, 15 ,false),
        TestCase(2, 20 ,false)
    )

    @Test
    fun `snow is disabled if time is out of range`() {
        initUnderTest()

        testCases.forEach { (month, day, expectedEnabledState) ->
            every { mockTimeManager.now } returns LocalDateTime(2023, month, day, 12, 10)
            assertEquals(expectedEnabledState, underTest.invoke())
        }
    }

}