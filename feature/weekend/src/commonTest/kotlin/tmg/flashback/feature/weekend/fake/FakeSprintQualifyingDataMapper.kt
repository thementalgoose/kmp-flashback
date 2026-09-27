package tmg.flashback.feature.weekend.fake

import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingModel
import tmg.flashback.formula1.model.Race

class FakeSprintQualifyingDataMapper(
    var models: List<SprintQualifyingModel> = emptyList()
) : SprintQualifyingDataMapper {
    override fun invoke(race: Race): List<SprintQualifyingModel> {
        return models
    }
}
