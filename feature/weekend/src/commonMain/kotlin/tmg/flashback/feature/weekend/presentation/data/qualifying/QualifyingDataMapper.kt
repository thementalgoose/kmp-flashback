package tmg.flashback.feature.weekend.presentation.data.qualifying

import tmg.flashback.formula1.model.QualifyingType
import tmg.flashback.formula1.model.Race

interface QualifyingDataMapper {
    operator fun invoke(race: Race): List<QualifyingModel>
}

class QualifyingDataMapperImpl: QualifyingDataMapper {
    override fun invoke(race: Race): List<QualifyingModel> {
        return when {
            race.has(QualifyingType.Q3) -> race.getQ1Q2Q3QualifyingList()
            race.has(QualifyingType.Q2) -> race.getQ1Q2QualifyingList()
            race.has(QualifyingType.Q1) -> race.getQ1QualifyingList()
            else -> emptyList()
        }
    }

    private fun Race.getQ1Q2Q3QualifyingList(): List<QualifyingModel> {
        return qualifying
            .firstOrNull { it.label == QualifyingType.Q3 }
            ?.results
            ?.map {
                val overview = driverOverview(it.entry.driver.id)
                return@map QualifyingModel.Q1Q2Q3(
                    driver = it.entry,
                    finalQualifyingPosition = overview?.qualified,
                    q1 = overview?.q1,
                    q2 = overview?.q2,
                    q3 = overview?.q3,
                    grid = overview?.race?.grid,
                    sprintRaceGrid = when (this.raceInfo.season) {
                        2021, 2022 -> overview?.sprintRace?.grid
                        else -> null
                    }
                )
            }
            ?.sortedBy { it.qualified }
            ?: emptyList()
    }

    private fun Race.getQ1Q2QualifyingList(): List<QualifyingModel> {
        return qualifying.firstOrNull()
            ?.results
            ?.map {
                val overview = driverOverview(it.entry.driver.id)
                return@map QualifyingModel.Q1Q2(
                    driver = it.entry,
                    finalQualifyingPosition = overview?.qualified,
                    q1 = overview?.q1,
                    q2 = overview?.q2
                )
            }
            ?.sortedBy { it.qualified }
            ?: emptyList()
    }

    private fun Race.getQ1QualifyingList(): List<QualifyingModel> {
        return qualifying.firstOrNull()
            ?.results
            ?.map {
                val overview = driverOverview(it.entry.driver.id)
                return@map QualifyingModel.Q1(
                    driver = it.entry,
                    finalQualifyingPosition = overview?.qualified,
                    q1 = overview?.q1,
                )
            }
            ?.sortedBy { it.qualified }
            ?: emptyList()
    }
}