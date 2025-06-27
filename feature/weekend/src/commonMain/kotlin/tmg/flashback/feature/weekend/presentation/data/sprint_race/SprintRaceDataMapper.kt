package tmg.flashback.feature.weekend.presentation.data.sprint_race

import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.formula1.model.Race

interface SprintRaceDataMapper {
    operator fun invoke(race: Race, resultType: ResultType): List<SprintRaceModel>
}

internal class SprintRaceDataMapperImpl(): SprintRaceDataMapper {
    override fun invoke(race: Race, resultType: ResultType): List<SprintRaceModel> {
        return when (resultType) {
            ResultType.DRIVERS -> race
                .sprint
                .race
                .map { model ->
                    SprintRaceModel.DriverResult(model)
                }
                .sortedBy { it.result.finish }
            ResultType.CONSTRUCTORS -> race
                .sprint
                .race
                .groupBy { it.entry.constructor }
                .map { (constructor, listOfResult) ->
                    SprintRaceModel.ConstructorResult(
                        constructor = constructor,
                        points = listOfResult.sumOf { it.points },
                        position = 0,
                        drivers = listOfResult.map {
                            it.entry.driver to it.points
                        },
                        maxTeamPoints = 0.0,
                        highestDriverPosition = listOfResult.minOf { it.finish }
                    )
                }
                .sortedBy { it.highestDriverPosition }
                .sortedByDescending { it.points }
                .mapIndexed { index, constructorResult ->
                    constructorResult.copy(
                        position = index + 1,
                        maxTeamPoints = 15.0
                    )
                }
        }
    }
}