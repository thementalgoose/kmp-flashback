package tmg.flashback.feature.glossary.presentation.details

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import tmg.flashback.formula1.constants.Glossary
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class GlossaryDetailViewModelTest {

    private lateinit var underTest: GlossaryDetailViewModel

    private fun initUnderTest() {
        underTest = GlossaryDetailViewModel()
    }

    @Test
    fun `initial uiState has null glossary`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val state = awaitItem()
            assertNull(state.glossary)
        }
    }

    @Test
    fun `load matching id sets glossary enum in state`() = runTest {
        initUnderTest()

        val targetEntry = Glossary.entries.first()

        underTest.uiState.test {
            assertEquals(null, awaitItem().glossary)

            underTest.load(targetEntry.id)

            assertEquals(targetEntry, awaitItem().glossary)
        }
    }
}
