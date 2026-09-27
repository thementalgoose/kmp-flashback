package tmg.flashback.feature.weekend.fake

import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceModel
import tmg.flashback.formula1.model.Race

class FakeSprintRaceDataMapper(
    var models: List<SprintRaceModel> = emptyList()
) : SprintRaceDataMapper {
    override fun invoke(race: Race, resultType: ResultType): List<SprintRaceModel> {
        return models
    }
}
