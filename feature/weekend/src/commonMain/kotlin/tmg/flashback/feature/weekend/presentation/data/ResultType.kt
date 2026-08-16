package tmg.flashback.feature.weekend.presentation.data

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_constructors
import flashback.presentation.localisation.generated.resources.nav_drivers
import org.jetbrains.compose.resources.StringResource

enum class ResultType {
    DRIVERS,
    CONSTRUCTORS
}

internal val ResultType.label: StringResource
    get() = when (this) {
        ResultType.DRIVERS -> string.nav_drivers
        ResultType.CONSTRUCTORS -> string.nav_constructors
    }