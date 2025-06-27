package tmg.flashback.feature.weekend.presentation.data.sprint_qualifying

import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.SprintQualifyingType

interface SprintQualifyingDataMapper {
    operator fun invoke(race: Race): List<SprintQualifyingModel>
}

internal class SprintQualifyingDataMapperImpl(): SprintQualifyingDataMapper {
    override fun invoke(race: Race): List<SprintQualifyingModel> {
        val list = race.sprint.qualifying.firstOrNull { it.label == SprintQualifyingType.SQ3 } ?: return emptyList()

        return list.results
            .mapIndexed { index, it ->
                val overview = race.driverOverview(it.entry.driver.id)
                return@mapIndexed SprintQualifyingModel.Result(
                    driver = it.entry,
                    finalQualifyingPosition = overview?.sprintQ3?.position ?: overview?.sprintQ2?.position ?: overview?.sprintQ1?.position ?: (index + 1),
                    sq1 = overview?.sprintQ1,
                    sq2 = overview?.sprintQ2,
                    sq3 = overview?.sprintQ3,
                    grid = overview?.sprintRace?.grid
                )
            }
    }
}