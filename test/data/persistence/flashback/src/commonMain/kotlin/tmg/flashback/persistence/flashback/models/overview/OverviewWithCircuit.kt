package tmg.flashback.persistence.flashback.models.overview

import tmg.flashback.persistence.flashback.models.circuit.Circuit
import tmg.flashback.persistence.flashback.models.circuit.model

fun OverviewWithCircuit.Companion.model(
    overview: Overview = Overview.model(),
    circuit: Circuit = Circuit.model(),
    schedule: List<Schedule> = listOf(
        Schedule.model()
    )
): OverviewWithCircuit = OverviewWithCircuit(
    overview = overview,
    circuit = circuit,
    schedule = schedule
)