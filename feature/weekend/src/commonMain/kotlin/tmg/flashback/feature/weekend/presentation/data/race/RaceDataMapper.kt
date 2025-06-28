package tmg.flashback.feature.weekend.presentation.data.race

import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.formula1.model.Race

interface RaceDataMapper {
    operator fun invoke(race: Race, resultType: ResultType): List<RaceModel>
}

internal class RaceDataMapperImpl(): RaceDataMapper {
    override fun invoke(
        race: Race,
        resultType: ResultType
    ): List<RaceModel> {
        return when (resultType) {
            ResultType.DRIVERS -> {
                race.race.map { RaceModel.DriverResult(it) }
            }
            ResultType.CONSTRUCTORS -> race.race
                .groupBy { it.entry.constructor }
                .map { (constructor, listOfResult) ->
                    RaceModel.ConstructorResult(
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
                        maxTeamPoints = 45.0
                    )
                }
        }
    }
}