package tmg.flashback.feature.notifications.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.notifications.manager.NotificationManager
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class IsNotificationEnabledUseCaseTest {

    private val mockNotificationSettingsRepository: NotificationSettingsRepository = mock(autoUnit)
    private val mockNotificationManager: NotificationManager = mock(autoUnit)

    private val underTest: IsNotificationEnabledUseCase = IsNotificationEnabledUseCaseImpl(
        notificationSettingsRepository = mockNotificationSettingsRepository,
        notificationManager = mockNotificationManager,
    )

    @Test
    fun `upcoming returns true when setting enabled and channel is active`() {
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns setOf(NotificationUpcoming.RACE)
        every { mockNotificationManager.isChannelActive(NotificationUpcoming.RACE.channelId) } returns true

        val result = underTest(NotificationUpcoming.RACE)

        assertTrue(result)
    }

    @Test
    fun `upcoming returns false when setting disabled and channel is active`() {
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns emptySet()
        every { mockNotificationManager.isChannelActive(NotificationUpcoming.RACE.channelId) } returns true

        val result = underTest(NotificationUpcoming.RACE)

        assertFalse(result)
    }

    @Test
    fun `upcoming returns false when setting enabled but channel is inactive`() {
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns setOf(NotificationUpcoming.RACE)
        every { mockNotificationManager.isChannelActive(NotificationUpcoming.RACE.channelId) } returns false

        val result = underTest(NotificationUpcoming.RACE)

        assertFalse(result)
    }

    @Test
    fun `upcoming returns false when setting disabled and channel is inactive`() {
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns emptySet()
        every { mockNotificationManager.isChannelActive(NotificationUpcoming.RACE.channelId) } returns false

        val result = underTest(NotificationUpcoming.RACE)

        assertFalse(result)
    }

    @Test
    fun `results available returns true when setting enabled and channel is active`() {
        every { mockNotificationSettingsRepository.notificationResultsEnabled } returns setOf(NotificationResultsAvailable.RACE)
        every { mockNotificationManager.isChannelActive(NotificationResultsAvailable.RACE.channelId) } returns true

        val result = underTest(NotificationResultsAvailable.RACE)

        assertTrue(result)
    }

    @Test
    fun `results available returns false when setting disabled and channel is active`() {
        every { mockNotificationSettingsRepository.notificationResultsEnabled } returns emptySet()
        every { mockNotificationManager.isChannelActive(NotificationResultsAvailable.RACE.channelId) } returns true

        val result = underTest(NotificationResultsAvailable.RACE)

        assertFalse(result)
    }

    @Test
    fun `results available returns false when setting enabled but channel is inactive`() {
        every { mockNotificationSettingsRepository.notificationResultsEnabled } returns setOf(NotificationResultsAvailable.RACE)
        every { mockNotificationManager.isChannelActive(NotificationResultsAvailable.RACE.channelId) } returns false

        val result = underTest(NotificationResultsAvailable.RACE)

        assertFalse(result)
    }

    @Test
    fun `results available returns false when setting disabled and channel is inactive`() {
        every { mockNotificationSettingsRepository.notificationResultsEnabled } returns emptySet()
        every { mockNotificationManager.isChannelActive(NotificationResultsAvailable.RACE.channelId) } returns false

        val result = underTest(NotificationResultsAvailable.RACE)

        assertFalse(result)
    }
}
