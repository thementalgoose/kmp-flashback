package tmg.flashback.data.repo.mappers.app

import tmg.flashback.formula1.model.LineupSeason

class LineupMapper(
    private val driverDataMapper: DriverDataMapper,
    private val constructorDataMapper: ConstructorDataMapper
) {
    fun mapLineup(model: List<tmg.flashback.persistence.flashback.models.lineup.LineupWithDrivers>): List<LineupSeason>? {
        if (model.isEmpty()) {
            return null
        }
        return model.groupBy { it.lineup.season }
            .map {
                LineupSeason(
                    season = it.key,
                    driversToConstructors = it.value
                        .associate {
                            val driver = driverDataMapper.mapDriver(it.driver)
                            val constructor =
                                constructorDataMapper.mapConstructorData(it.constructor)
                            driver to constructor
                        }
                )
            }
    }
}