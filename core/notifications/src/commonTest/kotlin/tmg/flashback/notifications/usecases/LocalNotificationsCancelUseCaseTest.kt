package tmg.flashback.notifications.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.notifications.manager.NotificationManager
import tmg.flashback.notifications.repositories.NotificationRepository
import kotlin.test.Test

internal class LocalNotificationsCancelUseCaseTest {

    private val mockNotificationManager: NotificationManager = mock(autoUnit)
    private val mockNotificationRepository: NotificationRepository = mock(autoUnit)

    private lateinit var underTest: LocalNotificationsCancelUseCaseImpl

    private fun initUnderTest() {
        underTest = LocalNotificationsCancelUseCaseImpl(
            notificationManager = mockNotificationManager,
            notificationRepository = mockNotificationRepository
        )
    }

    //region cancel

    @Test
    fun `cancel cancels notification via notification manager`() {
        val notificationId = 123

        initUnderTest()
        underTest.cancel(notificationId)

        verify {
            mockNotificationManager.cancel(notificationId)
        }
    }

    @Test
    fun `cancel forwards notification id to manager`() {
        val notificationId = 456

        initUnderTest()
        underTest.cancel(notificationId)

        verify {
            mockNotificationManager.cancel(456)
        }
    }

    @Test
    fun `cancel handles zero notification id`() {
        initUnderTest()
        underTest.cancel(0)

        verify {
            mockNotificationManager.cancel(0)
        }
    }

    @Test
    fun `cancel handles negative notification id`() {
        initUnderTest()
        underTest.cancel(-1)

        verify {
            mockNotificationManager.cancel(-1)
        }
    }

    //endregion

    //region cancelAll

    @Test
    fun `cancelAll retrieves notification ids from repository`() {
        every { mockNotificationRepository.notificationIds } returns emptySet()

        initUnderTest()
        underTest.cancelAll()

        verify {
            mockNotificationRepository.notificationIds
        }
    }

    @Test
    fun `cancelAll cancels all notifications from repository`() {
        val notificationIds = setOf(1, 2, 3)
        every { mockNotificationRepository.notificationIds } returns notificationIds

        initUnderTest()
        underTest.cancelAll()

        verify {
            mockNotificationManager.cancelAll(listOf(1, 2, 3))
        }
    }

    @Test
    fun `cancelAll handles empty notification ids`() {
        every { mockNotificationRepository.notificationIds } returns emptySet()

        initUnderTest()
        underTest.cancelAll()

        verify {
            mockNotificationManager.cancelAll(emptyList())
        }
    }

    @Test
    fun `cancelAll handles single notification id`() {
        every { mockNotificationRepository.notificationIds } returns setOf(42)

        initUnderTest()
        underTest.cancelAll()

        verify {
            mockNotificationManager.cancelAll(listOf(42))
        }
    }

    @Test
    fun `cancelAll converts set to list for manager`() {
        val notificationIds = setOf(5, 10, 15)
        every { mockNotificationRepository.notificationIds } returns notificationIds

        initUnderTest()
        underTest.cancelAll()

        verify {
            mockNotificationManager.cancelAll(notificationIds.toList())
        }
    }

    //endregion
}
