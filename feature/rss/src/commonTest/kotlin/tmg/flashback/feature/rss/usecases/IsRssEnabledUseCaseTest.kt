package tmg.flashback.feature.rss.usecases

import tmg.flashback.feature.rss.repositories.FakeRssRepository
import kotlin.test.Test
import kotlin.test.assertTrue

internal class IsRssEnabledUseCaseTest {

    private lateinit var underTest: IsRssEnabledUseCaseImpl

    private val fakeRssRepository = FakeRssRepository()

    private fun initUnderTest() {
        underTest = IsRssEnabledUseCaseImpl(
            rssRepository = fakeRssRepository
        )
    }

    @Test
    fun `is feature enabled returns from repository`() {
        initUnderTest()

        fakeRssRepository.isEnabled = true

        assertTrue(underTest.invoke())
    }
}