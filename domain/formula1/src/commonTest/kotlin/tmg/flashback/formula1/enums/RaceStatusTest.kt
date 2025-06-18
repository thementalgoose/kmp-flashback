package tmg.flashback.formula1.enums

import tmg.flashback.formula1.enums.RaceStatus.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RaceStatusTest {
    
    data class TestCase(
        val expected: RaceStatus,
        val isStatusFinished: Boolean,
        val value: String
    )
    val testCases = listOf(
        TestCase(FINISHED,true,"Finished"),
        TestCase(FINISHED,true,"Finish"),
        TestCase(LAPS_1,true,"+1 Lap"),
        TestCase(LAPS_1,true,"+1 Laps"),
        TestCase(LAPS_2,true,"+2 Laps"),
        TestCase(LAPS_3,true,"+3 Laps"),
        TestCase(LAPS_4,true,"+4 Laps"),
        TestCase(LAPS_5,true,"+5 Laps"),
        TestCase(LAPS_6,true,"+6 Laps"),
        TestCase(LAPS_7,true,"+7 Laps"),
        TestCase(LAPS_8,true,"+8 Laps"),
        TestCase(LAPS_9,true,"+9 Laps"),
        TestCase(ENGINE,false,"Engine"),
        TestCase(WHEEL_NUT,false,"Wheel nut"),
        TestCase(TYRE,false,"Tyre"),
        TestCase(WHEEL,false,"Wheel"),
        TestCase(DAMAGE,false,"Damage"),
        TestCase(STEERING,false,"Steering"),
        TestCase(BRAKES,false,"Brakes"),
        TestCase(VIBRATIONS,false,"Vibrations"),
        TestCase(SUSPENSION,false,"Suspension"),
        TestCase(POWER_UNIT,false,"Power Unit"),
        TestCase(HYDRAULICS,false,"Hydraulics"),
        TestCase(WATER_LEAK,false,"Water leak"),
        TestCase(MECHANICAL,false,"Mechanical"),
        TestCase(GEARBOX,false,"Gearbox"),
        TestCase(ILLNESS,false,"Illness"),
        TestCase(DEBRIS,false,"Debris"),
        TestCase(COLLISION,false,"Collision"),
        TestCase(COLLISION,false,"Collision damage"),
        TestCase(POWER_LOSS,false,"Power loss"),
        TestCase(WITHDREW,false,"Withdrew"),
        TestCase(WITHDREW,false,"Retired"),
        TestCase(ACCIDENT,false,"Accident"),
        TestCase(OIL_PRESSURE,false,"Oil pressure"),
        TestCase(DISQUALIFIED,false,"Disqualified"),
        TestCase(PUNCTURE,false,"Puncture"),
        TestCase(ERS,false,"ERS"),
        TestCase(ELECTRICAL,false,"Electrical"),
        TestCase(ELECTRONICS,false,"Electronics"),
        TestCase(ELECTRONIC,false,"Electronic"),
        TestCase(DRIVESHAFT,false,"Driveshaft"),
        TestCase(FUEL_PRESSURE,false,"Fuel pressure"),
        TestCase(SPUN_OFF,false,"Spun off"),
        TestCase(TURBO,false,"Turbo"),
        TestCase(FUEL_SYSTEM,false,"Fuel system"),
        TestCase(TRANSMISSION,false,"Transmission"),
        TestCase(CLUTCH,false,"Clutch"),
        TestCase(OIL_LEAK,false,"Oil leak"),
        TestCase(EXHAUST,false,"Exhaust"),
        TestCase(DRIVETRAIN,false,"Drivetrain"),
        TestCase(REAR_WING,false,"Rear wing"),
        TestCase(FRONT_WING,false,"Front wing"),
        TestCase(WATER_PRESSURE,false,"Water pressure"),
        TestCase(SEAT,false,"Seat"),
        TestCase(BATTERY,false,"Battery"),
        TestCase(OUT_OF_FUEL,false,"Out of fuel"),
        TestCase(OVERHEATING,false,"Overheating"),
        TestCase(SPARK_PLUGS,false,"Spark plugs"),
        TestCase(THROTTLE,false,"Throttle")
    )

    @Test
    fun `race status maps to expected enum type`() {
        testCases.forEach { (expected, isStatusFinished, value) ->
            assertEquals(expected, RaceStatus.from(value))
            assertEquals(isStatusFinished, expected.isStatusFinished())
        }
    }

    @Test
    fun `race status maps mismatched capitalisation`() {
        assertEquals(TRANSMISSION, RaceStatus.from("TransMission"))
        assertEquals(TRANSMISSION, RaceStatus.from("TRANSMISSION"))
        assertEquals(TRANSMISSION, RaceStatus.from("transmission"))
    }

    @Test
    fun `race status maps unknown when not found`() {
        assertEquals(UNKNOWN, RaceStatus.from("Help"))
        assertEquals(UNKNOWN, RaceStatus.from("Yeeted"))
    }
}