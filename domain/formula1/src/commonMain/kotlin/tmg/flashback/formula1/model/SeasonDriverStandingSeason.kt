package tmg.flashback.formula1.model

data class SeasonDriverStandingSeason(
    val season: Int,
    val driver: Driver,
    val points: Double,
    val inProgress: Boolean,
    val inProgressName: String?,
    val inProgressRound: Int?,
    val races: Int,
    val championshipPosition: Int?,
    val constructors: List<SeasonDriverStandingSeasonConstructor>
) {

    val hasValidChampionshipPosition: Boolean by lazy {
        when (championshipPosition) {
            null -> false
            0 -> false
            else -> true
        }
    }

    val inProgressContent: Pair<String, Int>? by lazy {
        if (inProgress && inProgressName != null && inProgressRound != null) {
            Pair(inProgressName, inProgressRound)
        } else {
            null
        }
    }

    companion object
}