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
import flashback.presentation.localisation.generated.resources.glossary_active_aero_desc
import flashback.presentation.localisation.generated.resources.glossary_atr_desc
import flashback.presentation.localisation.generated.resources.glossary_barge_boards_desc
import flashback.presentation.localisation.generated.resources.glossary_box_desc
import flashback.presentation.localisation.generated.resources.glossary_clipping_desc
import flashback.presentation.localisation.generated.resources.glossary_cost_cap_desc
import flashback.presentation.localisation.generated.resources.glossary_das_desc
import flashback.presentation.localisation.generated.resources.glossary_delta_desc
import flashback.presentation.localisation.generated.resources.glossary_dirty_air_desc
import flashback.presentation.localisation.generated.resources.glossary_ers_desc
import flashback.presentation.localisation.generated.resources.glossary_es_desc
import flashback.presentation.localisation.generated.resources.glossary_fcy_desc
import flashback.presentation.localisation.generated.resources.glossary_graining_desc
import flashback.presentation.localisation.generated.resources.glossary_ground_effect_desc
import flashback.presentation.localisation.generated.resources.glossary_halo_desc
import flashback.presentation.localisation.generated.resources.glossary_hans_device_desc
import flashback.presentation.localisation.generated.resources.glossary_ice_desc
import flashback.presentation.localisation.generated.resources.glossary_kers_desc
import flashback.presentation.localisation.generated.resources.glossary_lico_desc
import flashback.presentation.localisation.generated.resources.glossary_mgu_h_desc
import flashback.presentation.localisation.generated.resources.glossary_mgu_k_desc
import flashback.presentation.localisation.generated.resources.glossary_monocoque_desc
import flashback.presentation.localisation.generated.resources.glossary_overcut_desc
import flashback.presentation.localisation.generated.resources.glossary_overtake_mode_desc
import flashback.presentation.localisation.generated.resources.glossary_parc_ferme_desc
import flashback.presentation.localisation.generated.resources.glossary_plank
import flashback.presentation.localisation.generated.resources.glossary_plank_desc
import flashback.presentation.localisation.generated.resources.glossary_porpoising_desc
import flashback.presentation.localisation.generated.resources.glossary_pu_desc
import flashback.presentation.localisation.generated.resources.glossary_rake_desc
import flashback.presentation.localisation.generated.resources.glossary_recharge_mode_desc
import flashback.presentation.localisation.generated.resources.glossary_sprint_desc
import flashback.presentation.localisation.generated.resources.glossary_straight_corner_mode_desc
import flashback.presentation.localisation.generated.resources.glossary_super_clipping_desc
import flashback.presentation.localisation.generated.resources.glossary_survival_cell_desc
import flashback.presentation.localisation.generated.resources.glossary_undercut_desc
import flashback.presentation.localisation.generated.resources.glossary_vsc_desc
import org.jetbrains.compose.resources.StringResource

enum class Glossary(
    val id: String,
    val label: StringResource,
    val desc: StringResource,
    val range: IntRange? = null
) {
    ACTIVE_AERO(
        id = "ACTIVE_AERO",
        label = string.glossary_active_aero,
        desc = string.glossary_active_aero_desc,
        since = 2026
    ),
    ATR(
        id = "ATR",
        label = string.glossary_atr,
        desc = string.glossary_atr_desc,
        since = 2022
    ),
    BARGE_BOARDS(
        id = "BARGE_BOARDS",
        label = string.glossary_barge_boards,
        desc = string.glossary_barge_boards_desc,
        since = 1993, to = 2021
    ),
    BOX(
        id = "BOX",
        label = string.glossary_box,
        desc = string.glossary_box_desc,
    ),
    CLIPPING(
        id = "CLIPPING",
        label = string.glossary_clipping,
        desc = string.glossary_clipping_desc,
    ),
    COST_CAP(
        id = "COST_CAP",
        label = string.glossary_cost_cap,
        desc = string.glossary_cost_cap_desc,
        since = 2021
    ),
    DAS(
        id = "DAS",
        label = string.glossary_das,
        desc = string.glossary_das_desc,
        since = 2020, to = 2020
    ),
    DELTA(
        id = "DELTA",
        label = string.glossary_delta,
        desc = string.glossary_delta_desc,
    ),
    DIRTY_AIR(
        id = "DIRTY_AIR",
        label = string.glossary_dirty_air,
        desc = string.glossary_dirty_air_desc,
    ),
    ERS(
        id = "ERS",
        label = string.glossary_ers,
        desc = string.glossary_ers_desc,
    ),
    ES(
        id = "ES",
        label = string.glossary_es,
        desc = string.glossary_es_desc,
        since = 2014
    ),
    FCY(
        id = "FCY",
        label = string.glossary_fcy,
        desc = string.glossary_fcy_desc,
    ),
    GRAINING(
        id = "GRAINING",
        label = string.glossary_graining,
        desc = string.glossary_graining_desc,
    ),
    GROUND_EFFECT(
        id = "GROUND_EFFECT",
        label = string.glossary_ground_effect,
        desc = string.glossary_ground_effect_desc,
        since = 2022, to = 2025
    ),
    HALO(
        id = "HALO",
        label = string.glossary_halo,
        desc = string.glossary_halo_desc,
        since = 2018
    ),
    HANS_DEVICE(
        id = "HANS_DEVICE",
        label = string.glossary_hans_device,
        desc = string.glossary_hans_device_desc,
    ),
    ICE(
        id = "ICE",
        label = string.glossary_ice,
        desc = string.glossary_ice_desc,
    ),
    KERS(
        id = "KERS",
        label = string.glossary_kers,
        desc = string.glossary_kers_desc,
        since = 2009, to = 2013
    ),
    LICO(
        id = "LICO",
        label = string.glossary_lico,
        desc = string.glossary_lico_desc,
    ),
    MGU_K(
        id = "MGU_K",
        label = string.glossary_mgu_k,
        desc = string.glossary_mgu_k_desc,
        since = 2014
    ),
    MGU_H(
        id = "MGU_H",
        label = string.glossary_mgu_h,
        desc = string.glossary_mgu_h_desc,
        since = 2014, to = 2025
    ),
    MONOCOQUE(
        id = "MONOCOQUE",
        label = string.glossary_monocoque,
        desc = string.glossary_monocoque_desc,
    ),
    OVERCUT(
        id = "OVERCUT",
        label = string.glossary_overcut,
        desc = string.glossary_overcut_desc,
    ),
    OVERTAKE_MODE(
        id = "OVERTAKE_MODE",
        label = string.glossary_overtake_mode,
        desc = string.glossary_overtake_mode_desc,
        since = 2026
    ),
    PARC_FERME(
        id = "PARC_FERME",
        label = string.glossary_parc_ferme,
        desc = string.glossary_parc_ferme_desc,
    ),
    PLANK(
        id = "PLANK",
        label = string.glossary_plank,
        desc = string.glossary_plank_desc,
    ),
    PORPOISING(
        id = "PORPOISING",
        label = string.glossary_porpoising,
        desc = string.glossary_porpoising_desc,
        since = 2022
    ),
    PU(
        id = "PU",
        label = string.glossary_pu,
        desc = string.glossary_pu_desc,
    ),
    RAKE(
        id = "RAKE",
        label = string.glossary_rake,
        desc = string.glossary_rake_desc,
    ),
    RECHARGE_MODE(
        id = "RECHARGE_MODE",
        label = string.glossary_recharge_mode,
        desc = string.glossary_recharge_mode_desc,
        since = 2026
    ),
    SPRINT(
        id = "SPRINT",
        label = string.glossary_sprint,
        desc = string.glossary_sprint_desc,
        since = 2021
    ),
    STRAIGHT_CORNER_MODE(
        id = "STRAIGHT_CORNER_MODE",
        label = string.glossary_straight_corner_mode,
        desc = string.glossary_straight_corner_mode_desc,
        since = 2026
    ),
    SUPER_CLIPPING(
        id = "SUPER_CLIPPING",
        label = string.glossary_super_clipping,
        desc = string.glossary_super_clipping_desc,
    ),
    SURVIVAL_CELL(
        id = "SURVIVAL_CELL",
        label = string.glossary_survival_cell,
        desc = string.glossary_survival_cell_desc,
    ),
    UNDERCUT(
        id = "UNDERCUT",
        label = string.glossary_undercut,
        desc = string.glossary_undercut_desc,
    ),
    VSC(
        id = "VSC",
        label = string.glossary_vsc,
        desc = string.glossary_vsc_desc,
        since = 2015
    );

    constructor(
        id: String,
        label: StringResource,
        desc: StringResource,
        since: Int
    ) : this(id, label, desc, since..Int.MAX_VALUE)

    constructor(
        id: String,
        label: StringResource,
        desc: StringResource,
        since: Int,
        to: Int
    ) : this(id, label, desc, since..to)

}