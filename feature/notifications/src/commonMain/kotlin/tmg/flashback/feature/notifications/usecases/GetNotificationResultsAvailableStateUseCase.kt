package tmg.flashback.feature.notifications.usecases

import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationResultsAvailableState

interface GetNotificationResultsAvailableStateUseCase {
    operator fun invoke(): NotificationResultsAvailableState
}

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
