package tmg.flashback.feature.notifications.usecases

import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.model.NotificationUpcomingState

interface GetNotificationUpcomingStateUseCase {
    operator fun invoke(): NotificationUpcomingState
}

internal class GetNotificationUpcomingStateUseCaseImpl(
    private val isNotificationEnabledUseCase: IsNotificationEnabledUseCase,
): GetNotificationUpcomingStateUseCase {
    override fun invoke(): NotificationUpcomingState {
        return NotificationUpcomingState(
            race = isNotificationEnabledUseCase(NotificationUpcoming.RACE),
            sprint = isNotificationEnabledUseCase(NotificationUpcoming.SPRINT),
            sprintQualifying = isNotificationEnabledUseCase(NotificationUpcoming.SPRINT_QUALIFYING),
            qualifying = isNotificationEnabledUseCase(NotificationUpcoming.QUALIFYING),
            freePractice = isNotificationEnabledUseCase(NotificationUpcoming.FREE_PRACTICE),
            other = isNotificationEnabledUseCase(NotificationUpcoming.OTHER),
        )
    }
}
