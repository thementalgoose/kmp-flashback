package tmg.flashback.eastereggs.usecases

import kotlinx.datetime.format
import tmg.flashback.eastereggs.repository.EasterEggsRepository
import tmg.flashback.infrastructure.datetime.TimeManager
import tmg.flashback.infrastructure.datetime.dateFormatMMDD

interface IsSnowEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsSnowEnabledUseCaseImpl(
    private val easterEggsRepository: EasterEggsRepository,
    private val timeManager: TimeManager
): IsSnowEnabledUseCase {

    companion object {
        private const val startMonthAndDate = "12-20"
        private const val endMonthAndDate = "01-14"
    }

    override operator fun invoke(): Boolean {
        val monthDay = timeManager.now.date.format(dateFormatMMDD)
        if (monthDay >= startMonthAndDate || monthDay <= endMonthAndDate) {
            return true
        }

        return easterEggsRepository.isSnowEnabled
    }
}
