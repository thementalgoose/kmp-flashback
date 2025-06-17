package tmg.flashback.formula1.model

data class ConstructorHistorySeasonDriver(
    val driver: DriverEntry,
    val points: Double,
    val wins: Int,
    val races: Int,
    val podiums: Int,
    val finishesInPoints: Int,
    val polePosition: Int,
    val championshipStanding: Int?
) {
    companion object
}