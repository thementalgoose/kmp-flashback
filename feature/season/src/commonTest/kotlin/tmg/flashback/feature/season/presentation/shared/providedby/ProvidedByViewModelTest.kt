package tmg.flashback.feature.season.presentation.shared.providedby

import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.data.repo.repository.InfoRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class ProvidedByViewModelTest {

    private lateinit var underTest: ProvidedByViewModel

    private val mockInfoRepository: InfoRepository = mock(autofill)

    private fun initUnderTest(dataProvidedBy: String? = null) {
        every { mockInfoRepository.dataProvidedBy } returns dataProvidedBy

        underTest = ProvidedByViewModel(
            infoRepository = mockInfoRepository
        )
    }

    @Test
    fun `message returns null when repository returns null`() {
        initUnderTest(dataProvidedBy = null)

        assertNull(underTest.message)
    }

    @Test
    fun `message returns value from infoRepository`() {
        initUnderTest(dataProvidedBy = "Ergast F1 API")

        assertEquals("Ergast F1 API", underTest.message)
    }
}
