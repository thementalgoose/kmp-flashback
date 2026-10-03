package tmg.flashback.eastereggs.usecases

import tmg.flashback.eastereggs.repository.FakeEasterEggsRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

internal class IsSummerEnabledUseCaseTest {

    private val fakeEasterEggsRepository = FakeEasterEggsRepository()

    private lateinit var underTest: IsSummerEnabledUseCaseImpl

    private fun initUnderTest() {
        underTest = IsSummerEnabledUseCaseImpl(
            easterEggsRepository = fakeEasterEggsRepository
        )
    }

    @Test
    fun `summer is disabled if config value is disabled`() {
        initUnderTest()
        assertFalse(underTest.invoke())
    }

    @Test
    fun `summer is enabled if config value is enabled`() {
        fakeEasterEggsRepository.isSummerEnabled = true

        initUnderTest()
        assertTrue(underTest.invoke())
    }
}