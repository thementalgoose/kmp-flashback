package tmg.flashback.eastereggs.usecases

import tmg.flashback.eastereggs.repository.FakeEasterEggsRepository
import kotlin.test.Test
import kotlin.test.assertTrue

internal class IsUkraineEnabledUseCaseTest {

    private val fakeEasterEggsRepository = FakeEasterEggsRepository()

    private lateinit var underTest: IsUkraineEnabledUseCaseImpl

    private fun initUnderTest() {
        underTest = IsUkraineEnabledUseCaseImpl(
            easterEggsRepository = fakeEasterEggsRepository
        )
    }

    @Test
    fun `is enabled returns status from repository`() {
        initUnderTest()

        fakeEasterEggsRepository.isUkraineEnabled = true

        assertTrue(underTest())
    }
}