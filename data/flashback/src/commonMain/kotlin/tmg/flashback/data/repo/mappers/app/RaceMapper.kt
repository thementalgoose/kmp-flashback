package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.race.QualifyingDriverResult
import tmg.flashback.persistence.flashback.models.race.RaceDriverResult
import tmg.flashback.persistence.flashback.models.race.RaceInfoWithCircuit
import tmg.flashback.persistence.flashback.models.race.SprintQualifyingDriverResult
import tmg.flashback.persistence.flashback.models.race.SprintRaceDriverResult
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.FastestLap
import tmg.flashback.formula1.model.QualifyingResult
import tmg.flashback.formula1.model.QualifyingRound
import tmg.flashback.formula1.model.QualifyingType.Q1
import tmg.flashback.formula1.model.QualifyingType.Q2
import tmg.flashback.formula1.model.QualifyingType.Q3
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.SprintQualifyingRound
import tmg.flashback.formula1.model.SprintQualifyingType.SQ1
import tmg.flashback.formula1.model.SprintQualifyingType.SQ2
import tmg.flashback.formula1.model.SprintQualifyingType.SQ3
import tmg.flashback.formula1.model.SprintRaceResult
import tmg.flashback.formula1.model.SprintResult
import tmg.flashback.formula1.utils.toLapTime
import tmg.flashback.infrastructure.datetime.fromTime
import tmg.flashback.infrastructure.datetime.requireFromDate

class RaceMapper(
    private val circuitMapper: CircuitMapper,
    private val driverDataMapper: DriverDataMapper,
    private val constructorDataMapper: ConstructorDataMapper,
    private val scheduleMapper: ScheduleMapper
) {

    fun mapRaceInfo(data: tmg.flashback.persistence.flashback.models.race.Race): RaceInfo {
        return RaceInfo(
            season = data.raceInfo.season,
            round = data.raceInfo.round,
            date = requireFromDate(data.raceInfo.date),
            time = fromTime(data.raceInfo.time),
            name = data.raceInfo.name,
            laps = data.raceInfo.laps,
            wikipediaUrl = data.raceInfo.wikiUrl,
            youtube = data.raceInfo.youtube,
            circuit = circuitMapper.mapCircuit(data.circuit)!!,
        )
    }

    fun mapRaceInfoWithCircuit(data: RaceInfoWithCircuit): RaceInfo {
        return RaceInfo(
            season = data.raceInfo.season,
            round = data.raceInfo.round,
            date = requireFromDate(data.raceInfo.date),
            time = fromTime(data.raceInfo.time),
            name = data.raceInfo.name,
            laps = data.raceInfo.laps,
            wikipediaUrl = data.raceInfo.wikiUrl,
            youtube = data.raceInfo.youtube,
            circuit = circuitMapper.mapCircuit(data.circuit)!!,
        )
    }

    fun mapRace(data: tmg.flashback.persistence.flashback.models.race.Race?): Race? {
        if (data == null) return null
        val race = mapRace(data.race)
        return Race(
            raceInfo = mapRaceInfo(data),
            sprint = mapSprint(data.sprintQualifying, data.sprintRace),
            qualifying = mapQualifying(data.qualifying),
            race = race,
            schedule = data.schedule.mapNotNull { scheduleMapper.mapSchedule(it) }
        )
    }


    private fun mapQualifying(input: List<QualifyingDriverResult>): List<QualifyingRound> {
        val qualifyingData: MutableList<QualifyingRound> = mutableListOf()

        // Q1
        if (input.any { it.qualifyingResult.q1 != null }) {
            val lapTimeOrder = input
                .sortedBy { it.qualifyingResult.q1?.toLapTime()?.totalMillis ?: Int.MAX_VALUE }
                .mapIndexed { index, item -> Pair(item.driver.id, index + 1) }
            val driverListForRound = input.map {
                QualifyingResult(
                    entry = DriverEntry(driverDataMapper.mapDriver(it.driver), constructorDataMapper.mapConstructorData(it.constructor)),
                    lapTime = it.qualifyingResult.q1?.toLapTime(),
                    position = lapTimeOrder.firstOrNull { (id, _) -> id == it.driver.id }?.second
                        ?: it.qualifyingResult.qualified
                )
            }
            qualifyingData.add(QualifyingRound(
                label = Q1,
                order = 1,
                results = driverListForRound.sortedBy { it.position }
            ))
        }
        // Q2
        if (input.any { it.qualifyingResult.q2 != null }) {
            val lapTimeOrder = input
                .sortedBy { it.qualifyingResult.q2?.toLapTime()?.totalMillis ?: Int.MAX_VALUE }
                .mapIndexed { index, item -> Pair(item.driver.id, index + 1) }
            val driverListForRound = input.map {
                QualifyingResult(
                    entry = DriverEntry(driverDataMapper.mapDriver(it.driver), constructorDataMapper.mapConstructorData(it.constructor)),
                    lapTime = it.qualifyingResult.q2?.toLapTime(),
                    position = lapTimeOrder.firstOrNull { (id, _) -> id == it.driver.id }?.second
                        ?: qualifyingData.firstOrNull { it.order == 1 }?.results?.firstOrNull { model -> model.entry.driver.id == it.driver.id }?.position
                        ?: it.qualifyingResult.qualified
                )
            }
            qualifyingData.add(QualifyingRound(
                label = Q2,
                order = 2,
                results = driverListForRound.sortedBy { it.position }
            ))
        }
        // Q3
        if (input.any { it.qualifyingResult.q3 != null }) {
            val lapTimeOrder = input
                .sortedBy { it.qualifyingResult.q3?.toLapTime()?.totalMillis ?: Int.MAX_VALUE }
                .mapIndexed { index, item -> Pair(item.driver.id, index + 1) }
            val driverListForRound = input.map {
                QualifyingResult(
                    entry = DriverEntry(driverDataMapper.mapDriver(it.driver), constructorDataMapper.mapConstructorData(it.constructor)),
                    lapTime = it.qualifyingResult.q3?.toLapTime(),
                    position = lapTimeOrder.firstOrNull { (id, _) -> id == it.driver.id }?.second
                        ?: qualifyingData.firstOrNull { it.order == 2 }?.results?.firstOrNull { model -> model.entry.driver.id == it.driver.id }?.position
                        ?: qualifyingData.firstOrNull { it.order == 1 }?.results?.firstOrNull { model -> model.entry.driver.id == it.driver.id }?.position
                        ?: it.qualifyingResult.qualified
                )
            }
            qualifyingData.add(QualifyingRound(
                label = Q3,
                order = 3,
                results = driverListForRound.sortedBy { it.position }
            ))
        }

        return qualifyingData
    }

    private fun mapSprint(sprintQualifying: List<SprintQualifyingDriverResult>, sprintRace: List<SprintRaceDriverResult>): SprintResult {
        return SprintResult(
            qualifying = mapSprintQualifying(sprintQualifying),
            race = sprintRace.map { result ->
                SprintRaceResult(
                    entry = DriverEntry(driverDataMapper.mapDriver(result.driver), constructorDataMapper.mapConstructorData(result.constructor)),
                    time = result.sprintResult.time?.toLapTime(),
                    points = result.sprintResult.points,
                    grid = result.sprintResult.gridPos ?: 0,
                    finish = result.sprintResult.finished,
                    status = RaceStatus.from(result.sprintResult.status)
                )
            }
        )
    }

    private fun mapSprintQualifying(input: List<SprintQualifyingDriverResult>): List<SprintQualifyingRound> {
        val qualifyingData: MutableList<SprintQualifyingRound> = mutableListOf()

        // SQ1
        if (input.any { it.qualifyingResult.sq1 != null }) {
            val lapTimeOrder = input
                .sortedBy { it.qualifyingResult.sq1?.toLapTime()?.totalMillis ?: Int.MAX_VALUE }
                .mapIndexed { index, item -> Pair(item.driver.id, index + 1) }
            val driverListForRound = input.map {
                tmg.flashback.formula1.model.SprintQualifyingResult(
                    entry = DriverEntry(driverDataMapper.mapDriver(it.driver), constructorDataMapper.mapConstructorData(it.constructor)),
                    lapTime = it.qualifyingResult.sq1?.toLapTime(),
                    position = lapTimeOrder.firstOrNull { (id, _) -> id == it.driver.id }?.second
                        ?: it.qualifyingResult.qualified
                )
            }
            qualifyingData.add(SprintQualifyingRound(
                label = SQ1,
                order = 1,
                results = driverListForRound.sortedBy { it.position }
            ))
        }
        // SQ2
        if (input.any { it.qualifyingResult.sq2 != null }) {
            val lapTimeOrder = input
                .sortedBy { it.qualifyingResult.sq2?.toLapTime()?.totalMillis ?: Int.MAX_VALUE }
                .mapIndexed { index, item -> Pair(item.driver.id, index + 1) }
            val driverListForRound = input.map {
                tmg.flashback.formula1.model.SprintQualifyingResult(
                    entry = DriverEntry(driverDataMapper.mapDriver(it.driver), constructorDataMapper.mapConstructorData(it.constructor)),
                    lapTime = it.qualifyingResult.sq2?.toLapTime(),
                    position = lapTimeOrder.firstOrNull { (id, _) -> id == it.driver.id }?.second
                        ?: qualifyingData.firstOrNull { it.order == 1 }?.results?.firstOrNull { model -> model.entry.driver.id == it.driver.id }?.position
                        ?: it.qualifyingResult.qualified
                )
            }
            qualifyingData.add(SprintQualifyingRound(
                label = SQ2,
                order = 2,
                results = driverListForRound.sortedBy { it.position }
            ))
        }
        // SQ3
        if (input.any { it.qualifyingResult.sq3 != null }) {
            val lapTimeOrder = input
                .sortedBy { it.qualifyingResult.sq3?.toLapTime()?.totalMillis ?: Int.MAX_VALUE }
                .mapIndexed { index, item -> Pair(item.driver.id, index + 1) }
            val driverListForRound = input.map {
                tmg.flashback.formula1.model.SprintQualifyingResult(
                    entry = DriverEntry(driverDataMapper.mapDriver(it.driver), constructorDataMapper.mapConstructorData(it.constructor)),
                    lapTime = it.qualifyingResult.sq3?.toLapTime(),
                    position = lapTimeOrder.firstOrNull { (id, _) -> id == it.driver.id }?.second
                        ?: qualifyingData.firstOrNull { it.order == 2 }?.results?.firstOrNull { model -> model.entry.driver.id == it.driver.id }?.position
                        ?: qualifyingData.firstOrNull { it.order == 1 }?.results?.firstOrNull { model -> model.entry.driver.id == it.driver.id }?.position
                        ?: it.qualifyingResult.qualified
                )
            }
            qualifyingData.add(SprintQualifyingRound(
                label = SQ3,
                order = 3,
                results = driverListForRound.sortedBy { it.position }
            ))
        }

        return qualifyingData
    }

    /**
     * @param input Map of driver id -> sprint quali result
     */
    private fun mapRace(input: List<RaceDriverResult>?): List<RaceResult> {
        if (input == null || input.isEmpty()) return emptyList()
        val allDrivers = input.map { result ->
            DriverEntry(
                driver = driverDataMapper.mapDriver(result.driver),
                constructor = constructorDataMapper.mapConstructorData(result.constructor)
            )
        }
        return input
            .map { race ->
                return@map allDrivers
                    .first { it.driver.id == race.driver.id }
                    .let { Pair(it, race.raceResult) }
            }
            .map { (driver, raceResult) ->
                RaceResult(
                    entry = driver,
                    time = raceResult.time?.toLapTime(),
                    points = raceResult.points,
                    grid = raceResult.gridPos ?: 0,
                    qualified = raceResult.qualified,
                    finish = raceResult.finished,
                    status = RaceStatus.from(raceResult.status),
                    fastestLap = raceResult.fastestLap?.let { mapFastestLap(it) }
                )
            }
            .sortedBy { it.finish }
    }

    private fun mapFastestLap(fastestLap: tmg.flashback.persistence.flashback.models.race.FastestLap?): FastestLap? {
        if (fastestLap == null) return null
        return FastestLap(
            rank = fastestLap.position,
            lapTime = fastestLap.time.toLapTime(),
        )
    }
}