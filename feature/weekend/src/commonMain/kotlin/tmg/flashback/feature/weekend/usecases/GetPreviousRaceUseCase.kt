package tmg.flashback.feature.weekend.usecases

import kotlinx.coroutines.flow.firstOrNull
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.formula1.model.OverviewRace

interface GetPreviousRaceUseCase {
    suspend operator fun invoke(season: Int, round: Int): OverviewRace?
}

internal class GetPreviousRaceUseCaseImpl(
    private val overviewRepository: OverviewRepository
): GetPreviousRaceUseCase {

    override suspend operator fun invoke(season: Int, round: Int): OverviewRace? {
        val race = overviewRepository.getOverview(season, round).firstOrNull() ?: return null
        return overviewRepository.getOverview(race.circuitId)
            .filter { race ->
                race.season < season || (race.season == season && race.round < round)
            }
            .sortedByDescending { race -> race.round }
            .maxByOrNull { race -> race.season }
    }
}
