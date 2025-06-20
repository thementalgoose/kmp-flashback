package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import tmg.flashback.formula1.enums.EventType

fun Event.Companion.model(
    label: String = "label",
    type: EventType = EventType.TESTING,
    date: LocalDate = LocalDate.of(2020, 10, 12)
): Event = Event(
    label = label,
    type = type,
    date = date
)