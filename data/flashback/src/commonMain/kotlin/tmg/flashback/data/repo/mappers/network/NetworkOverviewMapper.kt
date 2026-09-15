package tmg.flashback.data.repo.mappers.network

import org.koin.core.annotation.Single
import tmg.flashback.persistence.flashback.models.overview.Overview
import tmg.flashback.flashbackapi.api.models.overview.OverviewRace

@Single
class NetworkOverviewMapper {
    @Throws(RuntimeException::class)
    fun mapOverview(overview: OverviewRace?): Overview? {
        if (overview == null) return null
        return Overview(
            season = overview.season,
            round = overview.round,
            name = overview.name,
            circuitId = overview.circuit.id,
            laps = overview.laps,
            date = overview.date,
            time = overview.time,
            hasRace = overview.hasRace,
            hasSprint = overview.hasSprint,
            hasQualifying = overview.hasQualifying,
            cancelled = overview.cancelled,
        )
    }
}