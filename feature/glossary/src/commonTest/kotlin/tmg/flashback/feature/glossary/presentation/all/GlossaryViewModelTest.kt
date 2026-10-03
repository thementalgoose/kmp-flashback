package tmg.flashback.feature.glossary.presentation.all

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import tmg.flashback.formula1.constants.Glossary
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class GlossaryViewModelTest {

    private lateinit var underTest: GlossaryViewModel

    private fun initUnderTest() {
        underTest = GlossaryViewModel()
    }

    @Test
    fun `initial uiState contains default entries and null search term`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val state = awaitItem()
            assertNull(state.searchTerm)
            assertEquals(Glossary.entries, state.entries)
        }
    }
}
