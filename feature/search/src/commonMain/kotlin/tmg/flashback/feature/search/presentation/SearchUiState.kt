package tmg.flashback.feature.search.presentation

import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.ic_driver
import flashback.domain.formula1.generated.resources.ic_team
import flashback.domain.formula1.generated.resources.ic_track
import org.jetbrains.compose.resources.DrawableResource
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.OverviewRace

data class SearchUiState(
    val selectedCategory: Category?,
    val searchTerm: String,
    val isLoading: Boolean,
    val results: List<Result>,
)

sealed interface Result {
    val id: String
    val search: String

    data class ResultDriver(
        val driver: Driver,
        override val id: String = "driver-${driver.id}",
    ): Result {
        override val search: String by lazy {
            listOfNotNull(
                driver.id.lowercase(),
                driver.name.lowercase(),
                driver.nationality.lowercase(),
                driver.code?.lowercase(),
                driver.number?.toString()
            ).joinToString(separator = " ")
        }
    }

    data class ResultTeam(
        val constructor: Constructor,
        override val id: String = "team-${constructor.id}"
    ): Result {
        override val search: String by lazy {
            listOfNotNull(
                constructor.id.lowercase(),
                constructor.name.lowercase(),
                constructor.nationality.lowercase(),
            ).joinToString(separator = " ")
        }
    }

    data class ResultRace(
        val overviewRace: OverviewRace,
        override val id: String = "race-${overviewRace.season}-${overviewRace.round}-${overviewRace.raceName}"
    ): Result {
        override val search: String by lazy {
            listOfNotNull(
                overviewRace.season.toString(),
                overviewRace.round.toString(),
                overviewRace.raceName.lowercase(),
            ).joinToString(separator = " ")
        }
    }

    data class ResultCircuit(
        val circuit: Circuit,
        override val id: String = "circuit-${circuit.id}"
    ): Result {
        override val search: String by lazy {
            listOfNotNull(
                circuit.id.lowercase(),
                circuit.name.lowercase(),
                circuit.city.lowercase(),
                circuit.country.lowercase(),
            ).joinToString(separator = " ")
        }
    }
}

enum class Category(
    val key: String,
    val icon: DrawableResource,
) {
    Drivers(
        key = "Drivers",
        icon = Res.drawable.ic_driver
    ),
    Teams(
        key = "Teams",
        icon = Res.drawable.ic_team
    ),
    Circuits(
        key = "Circuits",
        icon = Res.drawable.ic_track
    ),
//    Races(key = "Races");
}