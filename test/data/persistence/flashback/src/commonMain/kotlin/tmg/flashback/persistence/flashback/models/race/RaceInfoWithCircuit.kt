package tmg.flashback.persistence.flashback.models.race

import tmg.flashback.persistence.flashback.models.circuit.Circuit
import tmg.flashback.persistence.flashback.models.circuit.model

fun RaceInfoWithCircuit.Companion.model(
    raceInfo: RaceInfo = RaceInfo.model(),
    circuit: Circuit = Circuit.model()
): RaceInfoWithCircuit = RaceInfoWithCircuit(
    raceInfo = raceInfo,
    circuit = circuit
)