package tmg.flashback.feature.notifications.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationResultsAvailableState
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GetNotificationResultsAvailableStateUseCaseTest {

    private val mockIsNotificationEnabledUseCase: IsNotificationEnabledUseCase = mock(autoUnit)

    private val underTest: GetNotificationResultsAvailableStateUseCase = GetNotificationResultsAvailableStateUseCaseImpl(
        isNotificationEnabledUseCase = mockIsNotificationEnabledUseCase,
    )

    @Test
    fun `invoke returns NotificationResultsAvailableState with corresponding values`() {
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.RACE) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.SPRINT) } returns false
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.SPRINT_QUALIFYING) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.QUALIFYING) } returns false

        val result = underTest()

        val expected = NotificationResultsAvailableState(
            race = true,
            sprint = false,
            sprintQualifying = true,
            qualifying = false,
        )
        assertEquals(expected, result)
    }

    @Test
    fun `invoke returns NotificationResultsAvailableState when all enabled`() {
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.RACE) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.SPRINT) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.SPRINT_QUALIFYING) } returns true
        every { mockIsNotificationEnabledUseCase(NotificationResultsAvailable.QUALIFYING) } returns true

        val result = underTest()

        val expected = NotificationResultsAvailableState(
            race = true,
            sprint = true,
            sprintQualifying = true,
            qualifying = true,
        )
        assertEquals(expected, result)
    }
}
