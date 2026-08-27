package tmg.flashback.formula1.constants

import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.glossary_active_aero
import flashback.presentation.localisation.generated.resources.glossary_atr
import flashback.presentation.localisation.generated.resources.glossary_barge_boards
import flashback.presentation.localisation.generated.resources.glossary_box
import flashback.presentation.localisation.generated.resources.glossary_clipping
import flashback.presentation.localisation.generated.resources.glossary_cost_cap
import flashback.presentation.localisation.generated.resources.glossary_das
import flashback.presentation.localisation.generated.resources.glossary_delta
import flashback.presentation.localisation.generated.resources.glossary_dirty_air
import flashback.presentation.localisation.generated.resources.glossary_ers
import flashback.presentation.localisation.generated.resources.glossary_es
import flashback.presentation.localisation.generated.resources.glossary_fcy
import flashback.presentation.localisation.generated.resources.glossary_graining
import flashback.presentation.localisation.generated.resources.glossary_ground_effect
import flashback.presentation.localisation.generated.resources.glossary_halo
import flashback.presentation.localisation.generated.resources.glossary_hans_device
import flashback.presentation.localisation.generated.resources.glossary_ice
import flashback.presentation.localisation.generated.resources.glossary_kers
import flashback.presentation.localisation.generated.resources.glossary_lico
import flashback.presentation.localisation.generated.resources.glossary_mgu_h
import flashback.presentation.localisation.generated.resources.glossary_mgu_k
import flashback.presentation.localisation.generated.resources.glossary_monocoque
import flashback.presentation.localisation.generated.resources.glossary_overcut
import flashback.presentation.localisation.generated.resources.glossary_overtake_mode
import flashback.presentation.localisation.generated.resources.glossary_parc_ferme
import flashback.presentation.localisation.generated.resources.glossary_porpoising
import flashback.presentation.localisation.generated.resources.glossary_pu
import flashback.presentation.localisation.generated.resources.glossary_rake
import flashback.presentation.localisation.generated.resources.glossary_recharge_mode
import flashback.presentation.localisation.generated.resources.glossary_sprint
import flashback.presentation.localisation.generated.resources.glossary_straight_corner_mode
import flashback.presentation.localisation.generated.resources.glossary_super_clipping
import flashback.presentation.localisation.generated.resources.glossary_survival_cell
import flashback.presentation.localisation.generated.resources.glossary_undercut
import flashback.presentation.localisation.generated.resources.glossary_vsc
import org.jetbrains.compose.resources.StringResource

enum class Glossary(
    val label: StringResource,
    val range: IntRange? = null
){
    ACTIVE_AERO(
        label = string.glossary_active_aero,
        since = 2026
    ),
    ATR(
        label = string.glossary_atr,
        since = 2022
    ),
    BARGE_BOARDS(
        label = string.glossary_barge_boards,
        since = 1993, to = 2021
    ),
    BOX(
        label = string.glossary_box,
    ),
    CLIPPING(
        label = string.glossary_clipping,
    ),
    COST_CAP(
        label = string.glossary_cost_cap,
        since = 2021
    ),
    DAS(
        label = string.glossary_das,
        since = 2020, to = 2020
    ),
    DELTA(
        label = string.glossary_delta,
    ),
    DIRTY_AIR(
        label = string.glossary_dirty_air
    ),
    ERS(
        label = string.glossary_ers,
    ),
    ES(
        label = string.glossary_es,
        since = 2014),
    FCY(
        label = string.glossary_fcy,
    ),
    GRAINING(
        label = string.glossary_graining,
    ),
    GROUND_EFFECT(
        label = string.glossary_ground_effect,
        since = 2022, to = 2025
    ),
    HALO(
        label = string.glossary_halo,
        since = 2018
    ),
    HANS_DEVICE(
        label = string.glossary_hans_device
    ),
    ICE(
        label = string.glossary_ice,
    ),
    KERS(
        label = string.glossary_kers,
        since = 2009, to = 2013
    ),
    LICO(
        label = string.glossary_lico,
    ),
    MGU_K(
        label = string.glossary_mgu_k,
        since = 2014
    ),
    MGU_H(
        label = string.glossary_mgu_h,
        since = 2014, to = 2025
    ),
    MONOCOQUE(
        label = string.glossary_monocoque,
    ),
    OVERCUT(
        label = string.glossary_overcut,
    ),
    OVERTAKE_MODE(
        label = string.glossary_overtake_mode,
        since = 2026
    ),
    PARC_FERME(
        label = string.glossary_parc_ferme,
    ),
    PLANK(
        label = string.glossary_plank,
    ),
    PORPOISING(
        label = string.glossary_porpoising,
        since = 2022
    ),
    PU(
        label = string.glossary_pu,
    ),
    RAKE(
        label = string.glossary_rake,
    ),
    RECHARGE_MODE(
        label = string.glossary_recharge_mode,
        since = 2026
    ),
    SPRINT(
        label = string.glossary_sprint,
        since = 2021
    ),
    STRAIGHT_CORNER_MODE(
        label = string.glossary_straight_corner_mode,
        since = 2026
    ),
    SUPER_CLIPPING(
        label = string.glossary_super_clipping,
    ),
    SURVIVAL_CELL(
        label = string.glossary_survival_cell,
    ),
    UNDERCUT(
        label = string.glossary_undercut,
    ),
    VSC(
        label = string.glossary_vsc,
        since = 2015
    );

    constructor(
        label: StringResource,
        since: Int
    ): this(label, since..Int.MAX_VALUE)

    constructor(
        label: StringResource,
        since: Int,
        to: Int
    ): this(label, since..to)

}