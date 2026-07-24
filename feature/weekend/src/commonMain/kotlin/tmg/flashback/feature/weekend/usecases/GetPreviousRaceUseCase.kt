package tmg.flashback.feature.weekend.usecases

import kotlinx.coroutines.flow.firstOrNull
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.formula1.model.OverviewRace

interface GetPreviousRaceUseCase {
    suspend operator fun invoke(currentRace: OverviewRace): OverviewRace?
}

internal class GetPreviousRaceUseCaseImpl(
    private val overviewRepository: OverviewRepository
): GetPreviousRaceUseCase {

    override suspend operator fun invoke(currentRace: OverviewRace): OverviewRace? {
        return overviewRepository.getOverview(currentRace.circuitId)
            .filter { race ->
                race.season < currentRace.season || (race.season == currentRace.season && race.round < currentRace.round)
            }
            .sortedBy { race -> race.round }
            .sortedBy { race -> race.season }
            .firstOrNull()
    }
}
