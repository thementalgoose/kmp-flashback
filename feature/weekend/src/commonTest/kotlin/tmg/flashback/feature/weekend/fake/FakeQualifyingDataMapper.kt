package tmg.flashback.feature.weekend.fake

import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingModel
import tmg.flashback.formula1.model.Race

class FakeQualifyingDataMapper(
    var models: List<QualifyingModel> = emptyList()
) : QualifyingDataMapper {
    override fun invoke(race: Race): List<QualifyingModel> {
        return models
    }
}
