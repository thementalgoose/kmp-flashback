package tmg.flashback.formula1.enums

import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import tmg.flashback.formula1.enums.RaceStatus.FINISHED
import tmg.flashback.formula1.enums.RaceStatus.LAPS_1
import tmg.flashback.formula1.enums.RaceStatus.LAPS_2
import tmg.flashback.formula1.enums.RaceStatus.LAPS_3
import tmg.flashback.formula1.enums.RaceStatus.LAPS_4
import tmg.flashback.formula1.enums.RaceStatus.LAPS_5
import tmg.flashback.formula1.enums.RaceStatus.LAPS_6
import tmg.flashback.formula1.enums.RaceStatus.LAPS_7
import tmg.flashback.formula1.enums.RaceStatus.LAPS_8
import tmg.flashback.formula1.enums.RaceStatus.LAPS_9

enum class RaceStatus(
    private val options: List<String>,
    private val icon: DrawableResource,
//    @StringRes
//    private val label: Int
) {
    FINISHED(listOf("Finished", "Finish"), Res.drawable.ic_status_finished),
    LAPS_1(listOf("+1 Lap", "+1 Laps"), Res.drawable.ic_status_lap_1),
    LAPS_2(listOf("+2 Laps"), Res.drawable.ic_status_lap_2),
    LAPS_3(listOf("+3 Laps"), Res.drawable.ic_status_lap_3),
    LAPS_4(listOf("+4 Laps"), Res.drawable.ic_status_lap_4),
    LAPS_5(listOf("+5 Laps"), Res.drawable.ic_status_lap_5),
    LAPS_6(listOf("+6 Laps"), Res.drawable.ic_status_lap_6),
    LAPS_7(listOf("+7 Laps"), Res.drawable.ic_status_lap_7),
    LAPS_8(listOf("+8 Laps"), Res.drawable.ic_status_lap_8),
    LAPS_9(listOf("+9 Laps"), Res.drawable.ic_status_lap_9),
    ENGINE(listOf("Engine"), Res.drawable.ic_status_engine),
    WHEEL_NUT(listOf("Wheel nut"), Res.drawable.ic_status_wheel_nut),
    TYRE(listOf("Tyre"), Res.drawable.ic_status_wheel),
    WHEEL(listOf("Wheel"), Res.drawable.ic_status_wheel),
    DAMAGE(listOf("Damage"), Res.drawable.ic_status_damage),
    STEERING(listOf("Steering"), Res.drawable.ic_status_steering),
    BRAKES(listOf("Brakes"), Res.drawable.ic_status_brakes),
    VIBRATIONS(listOf("Vibrations"), Res.drawable.ic_status_vibrations),
    SUSPENSION(listOf("Suspension"), Res.drawable.ic_status_suspension),
    POWER_UNIT(listOf("Power Unit"), Res.drawable.ic_status_power_unit),
    HYDRAULICS(listOf("Hydraulics"), Res.drawable.ic_status_hydraulic),
    WATER_LEAK(listOf("Water leak"), Res.drawable.ic_status_water_leak),
    MECHANICAL(listOf("Mechanical"), Res.drawable.ic_status_mechanical),
    GEARBOX(listOf("Gearbox"), Res.drawable.ic_status_gearbox),
    ILLNESS(listOf("Illness"), Res.drawable.ic_status_illness),
    DEBRIS(listOf("Debris"), Res.drawable.ic_status_damage),
    COLLISION(listOf("Collision", "Collision damage"), Res.drawable.ic_status_collision),
    POWER_LOSS(listOf("Power loss"), Res.drawable.ic_status_power_loss),
    WITHDREW(listOf("Withdrew", "Retired"), Res.drawable.ic_status_retired),
    ACCIDENT(listOf("Accident"), Res.drawable.ic_status_accident),
    OIL_PRESSURE(listOf("Oil pressure"), Res.drawable.ic_status_oil_pressure),
    DISQUALIFIED(listOf("Disqualified"), Res.drawable.ic_status_disqualified),
    PUNCTURE(listOf("Puncture"), Res.drawable.ic_status_puncture),
    ERS(listOf("ERS"), Res.drawable.ic_status_electrical),
    ELECTRICAL(listOf("Electrical"), Res.drawable.ic_status_electrical),
    ELECTRONICS(listOf("Electronics"), Res.drawable.ic_status_electrical),
    ELECTRONIC(listOf("Electronic"), Res.drawable.ic_status_electrical),
    DRIVESHAFT(listOf("Driveshaft"), Res.drawable.ic_status_driveshaft),
    FUEL_PRESSURE(listOf("Fuel pressure"), Res.drawable.ic_status_fuel_system),
    SPUN_OFF(listOf("Spun off"), Res.drawable.ic_status_spun_off),
    TURBO(listOf("Turbo"), Res.drawable.ic_status_turbo),
    FUEL_SYSTEM(listOf("Fuel system"), Res.drawable.ic_status_fuel_system),
    TRANSMISSION(listOf("Transmission"), Res.drawable.ic_status_transmission),
    CLUTCH(listOf("Clutch"), Res.drawable.ic_status_clutch),
    OIL_LEAK(listOf("Oil leak"), Res.drawable.ic_status_oil_leak),
    EXHAUST(listOf("Exhaust"), Res.drawable.ic_status_exhaust),
    DRIVETRAIN(listOf("Drivetrain"), Res.drawable.ic_status_driveshaft),
    REAR_WING(listOf("Rear wing"), Res.drawable.ic_status_rear_wing),
    FRONT_WING(listOf("Front wing"), Res.drawable.ic_status_front_wing),
    WATER_PRESSURE(listOf("Water pressure"), Res.drawable.ic_status_water_pressure),
    SEAT(listOf("Seat"), Res.drawable.ic_status_seat),
    BATTERY(listOf("Battery"), Res.drawable.ic_status_battery),
    OUT_OF_FUEL(listOf("Out of fuel"), Res.drawable.ic_status_fuel_system),
    OVERHEATING(listOf("Overheating"), Res.drawable.ic_status_fire),
    SPARK_PLUGS(listOf("Spark plugs"), Res.drawable.ic_status_power_loss),
    THROTTLE(listOf("Throttle"), Res.drawable.ic_status_turbo),
    UNKNOWN(listOf("Unknown"), Res.drawable.ic_status_unknown);

    val label: String = options.first()
    companion object {
        fun from(status: String): RaceStatus {
            return entries.firstOrNull { raceStatus -> raceStatus.options.any { it.lowercase() == status.lowercase() } }
                ?: UNKNOWN
        }
    }
}

fun RaceStatus.isStatusFinished(): Boolean {
    return this in listOf(
        FINISHED,
        LAPS_1,
        LAPS_2,
        LAPS_3,
        LAPS_4,
        LAPS_5,
        LAPS_6,
        LAPS_7,
        LAPS_8,
        LAPS_9
    )
}