package tmg.flashback.feature.season.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.data.repo.repository.InfoRepository
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.formula1.constants.Formula1
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DefaultSeasonUseCaseTest {

    private val mockCalendarRepository: CalendarRepository = mock(autoUnit)
    private val mockInfoRepository: InfoRepository = mock(autoUnit)

    private lateinit var underTest: DefaultSeasonUseCaseImpl

    private fun initUnderTest() {
        underTest = DefaultSeasonUseCaseImpl(
            infoRepository = mockInfoRepository,
            calendarRepository = mockCalendarRepository
        )
    }

    @Test
    fun `returns current year if supported season list is empty`() {
        every { mockInfoRepository.supportedSeasons } returns emptySet()
        every { mockInfoRepository.defaultSeason } returns 2018
        initUnderTest()

        assertEquals(Formula1.currentSeasonYear, underTest.defaultSeason)
    }

    @Test
    fun `returns users last selected season if pref is enabled and season is supported`() {
        every { mockInfoRepository.supportedSeasons } returns setOf(2016, 2017, 2018)
        every { mockCalendarRepository.userSelectedSeason } returns 2017
        every { mockInfoRepository.defaultSeason } returns 2016
        every { mockCalendarRepository.keepUserSelectedSeason } returns true

        initUnderTest()

        assertEquals(2017, underTest.defaultSeason)
    }

    @Test
    fun `returns server season if season if users last selected season pref is disabled`() {
        every { mockInfoRepository.supportedSeasons } returns setOf(2016, 2017, 2018)
        every { mockCalendarRepository.userSelectedSeason } returns 2017
        every { mockInfoRepository.defaultSeason } returns 2016
        every { mockCalendarRepository.keepUserSelectedSeason } returns false

        initUnderTest()
        assertEquals(2016, underTest.defaultSeason)
    }

    @Test
    fun `returns server season if season is supported`() {
        every { mockInfoRepository.supportedSeasons } returns setOf(2016, 2017, 2018)
        every { mockInfoRepository.defaultSeason } returns 2017
        every { mockCalendarRepository.keepUserSelectedSeason } returns false

        initUnderTest()
        assertEquals(2017, underTest.defaultSeason)
    }

    @Test
    fun `returns latest supported season if server season doesnt exist`() {
        every { mockInfoRepository.supportedSeasons } returns setOf(2016, 2017, 2018)
        every { mockInfoRepository.defaultSeason } returns 2020
        every { mockCalendarRepository.keepUserSelectedSeason } returns false

        initUnderTest()
        assertEquals(2018, underTest.defaultSeason)
    }
}