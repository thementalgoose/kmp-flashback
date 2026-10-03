package tmg.flashback.feature.season.presentation.shared.seasonpicker

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SeasonPickerViewModelTest {

    private lateinit var underTest: SeasonPickerViewModel

    private val mockCurrentSeasonHolder: CurrentSeasonHolder = mock(autoUnit)

    private val currentSeasonFlow = MutableStateFlow(2024)
    private val supportedSeasonsFlow = MutableStateFlow(listOf(2024, 2023, 2022))
    private val newSeasonAvailableFlow = MutableStateFlow(false)

    private fun initUnderTest() {
        every { mockCurrentSeasonHolder.currentSeasonFlow } returns currentSeasonFlow
        every { mockCurrentSeasonHolder.supportedSeasonsFlow } returns supportedSeasonsFlow
        every { mockCurrentSeasonHolder.newSeasonAvailableFlow } returns newSeasonAvailableFlow

        underTest = SeasonPickerViewModel(
            currentSeasonHolder = mockCurrentSeasonHolder
        )
    }

    @Test
    fun `flows reflect currentSeasonHolder flows`() {
        initUnderTest()

        assertEquals(2024, underTest.currentSeason.value)
        assertEquals(listOf(2024, 2023, 2022), underTest.supportedSeasons.value)
        assertEquals(false, underTest.newSeasonAvailable.value)
    }

    @Test
    fun `currentSeasonUpdate delegates to currentSeasonHolder updateTo`() {
        initUnderTest()

        underTest.currentSeasonUpdate(2023)

        verify {
            mockCurrentSeasonHolder.updateTo(2023)
        }
    }

    @Test
    fun `goToLatestSeason delegates to currentSeasonHolder updateToLatest`() {
        initUnderTest()

        underTest.goToLatestSeason()

        verify {
            mockCurrentSeasonHolder.updateToLatest()
        }
    }
}
