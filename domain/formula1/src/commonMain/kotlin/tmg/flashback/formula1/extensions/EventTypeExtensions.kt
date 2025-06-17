package tmg.flashback.formula1.extensions

import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.*
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.enums.EventType

val EventType.icon: DrawableResource
    get() = when (this) {
        EventType.TESTING -> Res.drawable.ic_event_type_testing
        EventType.CAR_LAUNCH -> Res.drawable.ic_event_type_car_launch
        EventType.NETFLIX -> Res.drawable.ic_event_type_netflix
        EventType.OTHER -> Res.drawable.ic_event_type_other
    }

val EventType.label: StringResource
    get() = when (this) {
        EventType.TESTING -> string.event_type_testing
        EventType.CAR_LAUNCH -> string.event_type_car_launch
        EventType.NETFLIX -> string.event_type_netflix
        EventType.OTHER -> string.event_type_other
    }