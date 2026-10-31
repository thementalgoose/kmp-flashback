package tmg.flashback.data.repo.mappers.app

import org.koin.core.annotation.Single
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.infrastructure.extensions.toColourInt

@Single
class ConstructorDataMapper {

    fun mapConstructorData(constructor: tmg.flashback.persistence.flashback.models.constructors.Constructor): Constructor {
        return Constructor(
            id = constructor.id,
            name = constructor.name,
            wikiUrl = constructor.wikiUrl,
            photoUrl = constructor.photoUrl,
            nationality = constructor.nationality,
            nationalityISO = constructor.nationalityISO,
            color = constructor.colour.toColourInt(),
        )
    }
}