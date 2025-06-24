package tmg.flashback.feature.season.usecases

import tmg.flashback.data.repo.repository.InfoRepository
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.formula1.constants.Formula1

interface DefaultSeasonUseCase {
    val defaultSeason: Int
}

internal class DefaultSeasonUseCaseImpl(
    private val infoRepository: InfoRepository,
    private val calendarRepository: CalendarRepository
): DefaultSeasonUseCase {
    override val defaultSeason: Int
        get() {
            val supportedSeasons = infoRepository.supportedSeasons
            // No season list available, so default to current yeah
            if (supportedSeasons.isEmpty()) {
                return Formula1.currentSeasonYear
            }


            val serverSeason = serverDefaultSeason
            if (calendarRepository.keepUserSelectedSeason) {
                val usersLastSeasonSelection = calendarRepository.userSelectedSeason ?: serverSeason
                if (supportedSeasons.contains(usersLastSeasonSelection)) {
                    return usersLastSeasonSelection
                } else {
                    // Users last viewed season has been removed
                    //  from supported seasons list. Reset the pref
                    calendarRepository.userSelectedSeason = null
                }
            }

            return if (!supportedSeasons.contains(serverDefaultSeason)) {
                supportedSeasons.max()
            } else {
                serverSeason
            }
        }

    private val serverDefaultSeason: Int
        get() = infoRepository.defaultSeason

}