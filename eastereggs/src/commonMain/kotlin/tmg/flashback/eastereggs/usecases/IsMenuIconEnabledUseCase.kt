package tmg.flashback.eastereggs.usecases

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.koin.core.annotation.Single
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.infrastructure.datetime.TimeManager
import tmg.flashback.infrastructure.datetime.now

interface IsMenuIconEnabledUseCase {
    operator fun invoke(): MenuIcons?
}

@Single
internal class IsMenuIconEnabledUseCaseImpl(
    private val timeManager: TimeManager
): IsMenuIconEnabledUseCase {
    override operator fun invoke(): MenuIcons? {
        return MenuIcons.entries
            .firstOrNull { it.isNow(timeManager.now.date) }
    }
}