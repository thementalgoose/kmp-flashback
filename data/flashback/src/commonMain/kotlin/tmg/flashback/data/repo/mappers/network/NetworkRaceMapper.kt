package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.race.FastestLap
import tmg.flashback.persistence.flashback.models.race.QualifyingResult
import tmg.flashback.persistence.flashback.models.race.RaceResult
import tmg.flashback.persistence.flashback.models.race.SprintQualifyingResult
import tmg.flashback.persistence.flashback.models.race.SprintRaceResult

class NetworkRaceMapper {

    @Throws(RuntimeException::class)
    fun mapRaceResults(season: Int, round: Int, data: tmg.flashback.flashbackapi.api.models.races.RaceResult): RaceResult {
        return RaceResult(
            driverId = data.driverId,
            season = season,
            round = round,
            constructorId = data.constructorId,
            points = data.points,
            qualified = data.qualified,
            gridPos = data.gridPos,
            finished = data.finished,
            status = data.status,
            time = data.time,
            fastestLap = mapFastestLap(data.fastestLap)
        )
    }

    @Throws(RuntimeException::class)
    fun mapQualifyingResults(season: Int, round: Int, data: tmg.flashback.flashbackapi.api.models.races.QualifyingResult): QualifyingResult {
        return QualifyingResult(
            driverId = data.driverId,
            season = season,
            round = round,
            constructorId = data.constructorId,
            qualified = data.qualified,
            q1 = data.q1,
            q2 = data.q2,
            q3 = data.q3
        )
    }

    @Throws(RuntimeException::class)
    fun mapSprintQualifyingResult(season: Int, round: Int, data: tmg.flashback.flashbackapi.api.models.races.SprintQualifyingResult): SprintQualifyingResult {
        return SprintQualifyingResult(
            driverId = data.driverId,
            season = season,
            round = round,
            constructorId = data.constructorId,
            qualified = data.qualified,
            sq1 = data.sq1,
            sq2 = data.sq2,
            sq3 = data.sq3
        )
    }

    @Throws(RuntimeException::class)
    fun mapSprintRaceResults(season: Int, round: Int, data: tmg.flashback.flashbackapi.api.models.races.SprintRaceResult): SprintRaceResult {
        return SprintRaceResult(
            driverId = data.driverId,
            season = season,
            round = round,
            constructorId = data.constructorId,
            points = data.points,
            gridPos = data.gridPos,
            finished = data.finished,
            status = data.status,
            time = data.time
        )
    }

    private fun mapFastestLap(data: tmg.flashback.flashbackapi.api.models.races.FastestLap?): FastestLap? {
        if (data == null) return null
        return FastestLap(
            position = data.position,
            time = data.time
        )
    }
}