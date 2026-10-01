package tmg.flashback.feature.weekend.fake

import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.feature.weekend.presentation.data.race.RaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.race.RaceModel
import tmg.flashback.formula1.model.Race

class FakeRaceDataMapper(
    var models: List<RaceModel> = emptyList()
) : RaceDataMapper {
    override fun invoke(race: Race, resultType: ResultType): List<RaceModel> {
        return models
    }
}
