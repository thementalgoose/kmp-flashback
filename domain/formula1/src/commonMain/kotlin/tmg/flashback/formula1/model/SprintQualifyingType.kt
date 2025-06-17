package tmg.flashback.formula1.model

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.sprint_qualifying_header_q1
import flashback.presentation.localisation.generated.resources.sprint_qualifying_header_q2
import flashback.presentation.localisation.generated.resources.sprint_qualifying_header_q3
import org.jetbrains.compose.resources.StringResource

enum class SprintQualifyingType {
    SQ1,
    SQ2,
    SQ3
}

val SprintQualifyingType.headerLabel: StringResource
    get() = when (this) {
        SprintQualifyingType.SQ1 -> string.sprint_qualifying_header_q1
        SprintQualifyingType.SQ2 -> string.sprint_qualifying_header_q2
        SprintQualifyingType.SQ3 -> string.sprint_qualifying_header_q3
    }