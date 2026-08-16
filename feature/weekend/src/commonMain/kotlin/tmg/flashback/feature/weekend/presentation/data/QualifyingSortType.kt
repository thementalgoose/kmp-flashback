package tmg.flashback.feature.weekend.presentation.data

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.qualifying_header_q1
import flashback.presentation.localisation.generated.resources.qualifying_header_q2
import flashback.presentation.localisation.generated.resources.qualifying_header_q3
import flashback.presentation.localisation.generated.resources.qualifying_header_result
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.feature.weekend.presentation.data.QualifyingSortType.Qualified

enum class QualifyingSortType {
    Qualified,
    Q3,
    Q2,
    Q1
}

internal val QualifyingSortType.label: StringResource
    get() = when (this) {
        Qualified -> string.qualifying_header_result
        QualifyingSortType.Q3 -> string.qualifying_header_q3
        QualifyingSortType.Q2 -> string.qualifying_header_q2
        QualifyingSortType.Q1 -> string.qualifying_header_q1
    }