package tmg.flashback.feature.notifications.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.model.NotificationUpcomingState
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GetNotificationUpcomingStateUseCaseTest {

    private val mockIsNotificationEnabledUseCase: IsNotificationEnabledUseCase = mock(autoUnit)

    private val underTest: GetNotificationUpcomingStateUseCase = GetNotificationUpcomingStateUseCaseImpl(
        isNotificationEnabledUseCase = mockIsNotificationEnabledUseCase,
    )

    @Test
    fun `invoke returns NotificationUpcomingState with corresponding values`() {
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.RACE) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.SPRINT) } returns false
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.SPRINT_QUALIFYING) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.QUALIFYING) } returns false
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.FREE_PRACTICE) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.OTHER) } returns false

        val result = underTest()

        val expected = NotificationUpcomingState(
            race = true,
            sprint = false,
            sprintQualifying = true,
            qualifying = false,
            freePractice = true,
            other = false,
        )
        assertEquals(expected, result)
    }

    @Test
    fun `invoke returns NotificationUpcomingState when all enabled`() {
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.RACE) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.SPRINT) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.SPRINT_QUALIFYING) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.QUALIFYING) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.FREE_PRACTICE) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationUpcoming.OTHER) } returns true

        val result = underTest()

        val expected = NotificationUpcomingState(
            race = true,
            sprint = true,
            sprintQualifying = true,
            qualifying = true,
            freePractice = true,
            other = true,
        )
        assertEquals(expected, result)
    }
}
