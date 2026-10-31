package tmg.flashback.data.repo.mappers.network

import org.koin.core.annotation.Single
import tmg.flashback.infrastructure.datetime.fromDate
import tmg.flashback.persistence.flashback.models.overview.Event

@Single
class NetworkEventMapper {

    fun mapEvent(season: Int, model: tmg.flashback.flashbackapi.api.models.overview.Event): Event? {
        fromDate(model.date) ?: return null
        return Event(
            season = season,
            label = model.label,
            type = model.type,
            date = model.date
        )
    }
}