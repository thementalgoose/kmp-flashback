package tmg.flashback.feature.notifications.usecases

import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationResultsAvailableState
import org.koin.core.annotation.Single

interface GetNotificationResultsAvailableStateUseCase {
    operator fun invoke(): NotificationResultsAvailableState
}

@Single
internal class GetNotificationResultsAvailableStateUseCaseImpl(
    private val isNotificationEnabledUseCase: IsNotificationEnabledUseCase,
): GetNotificationResultsAvailableStateUseCase {
    override fun invoke(): NotificationResultsAvailableState {
        return NotificationResultsAvailableState(
            race = isNotificationEnabledUseCase(NotificationResultsAvailable.RACE),
            sprint = isNotificationEnabledUseCase(NotificationResultsAvailable.SPRINT),
            sprintQualifying = isNotificationEnabledUseCase(NotificationResultsAvailable.SPRINT_QUALIFYING),
            qualifying = isNotificationEnabledUseCase(NotificationResultsAvailable.QUALIFYING),
        )
    }
}
