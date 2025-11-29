package tmg.flashback.feature.drivers.presentation.comparison

import dev.mokkery.MockMode.autofill
import dev.mokkery.mock
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.data.repo.repository.StandingsRepository
import kotlin.test.Test

internal class DriverComparisonViewModelTest {

    private val mockSeasonRepository: SeasonRepository = mock(autofill)
    private val mockRacesRepository: RaceRepository = mock(autofill)
    private val mockStandingsRepository: StandingsRepository = mock(autofill)

    private lateinit var underTest: DriverComparisonViewModel

    private fun initUnderTest() {
        underTest = DriverComparisonViewModel(
            seasonRepository = mockSeasonRepository,
            standingsRepository = mockStandingsRepository,
            racesRepository = mockRacesRepository,
        )
    }

    // TODO
}