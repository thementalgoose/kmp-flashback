package tmg.flashback.feature.season.presentation.shared.seasonpicker

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.data.repo.repository.InfoRepository
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.feature.season.usecases.DefaultSeasonUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CurrentSeasonHolderTest {

    private val mockDefaultSeasonUseCase: DefaultSeasonUseCase = mock(autoUnit)
    private val mockCalendarRepository: CalendarRepository = mock(autoUnit)
    private val mockInfoRepository: InfoRepository = mock(autoUnit)

    private lateinit var underTest: CurrentSeasonHolderImpl

    private fun initUnderTest(
        defaultSeason: Int = 2023,
        serverDefaultSeason: Int = 2024,
        supportedSeasons: Set<Int> = setOf(2022, 2023, 2024),
        viewedSeasons: Set<Int> = emptySet()
    ) {
        every { mockDefaultSeasonUseCase.defaultSeason } returns defaultSeason
        every { mockDefaultSeasonUseCase.serverDefaultSeason } returns serverDefaultSeason
        every { mockInfoRepository.supportedSeasons } returns supportedSeasons
        every { mockCalendarRepository.viewedSeasons } returns viewedSeasons

        underTest = CurrentSeasonHolderImpl(
            defaultSeasonUseCase = mockDefaultSeasonUseCase,
            calendarRepository = mockCalendarRepository,
            infoRepository = mockInfoRepository
        )
    }

    @Test
    fun `initialisation sets currentSeason to defaultSeasonUseCase defaultSeason`() {
        initUnderTest(defaultSeason = 2023)

        assertEquals(2023, underTest.currentSeason)
        assertEquals(2023, underTest.currentSeasonFlow.value)
    }

    @Test
    fun `updateToLatest updates to defaultSeasonUseCase serverDefaultSeason`() {
        initUnderTest(
            defaultSeason = 2022,
            serverDefaultSeason = 2024,
            supportedSeasons = setOf(2022, 2023, 2024)
        )

        underTest.updateToLatest()

        assertEquals(2024, underTest.currentSeason)
        assertEquals(2024, underTest.currentSeasonFlow.value)
        verify {
            mockCalendarRepository.userSelectedSeason = 2024
        }
    }

    @Test
    fun `updateTo updates currentSeason and userSelectedSeason`() {
        initUnderTest(
            defaultSeason = 2022,
            supportedSeasons = setOf(2022, 2023, 2024)
        )

        underTest.updateTo(2023)

        assertEquals(2023, underTest.currentSeason)
        assertEquals(2023, underTest.currentSeasonFlow.value)
        verify {
            mockCalendarRepository.userSelectedSeason = 2023
        }
    }
}
