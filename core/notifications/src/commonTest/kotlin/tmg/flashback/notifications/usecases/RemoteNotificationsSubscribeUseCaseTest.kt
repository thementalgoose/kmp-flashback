package tmg.flashback.notifications.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.runBlocking
import tmg.flashback.notifications.firebase.FirebaseMessagingService
import tmg.flashback.notifications.repositories.NotificationRepository
import kotlin.test.Test
import kotlin.test.assertTrue

internal class RemoteNotificationsSubscribeUseCaseTest {

    private val mockNotificationRepository: NotificationRepository = mock(autoUnit)
    private val mockFirebaseMessagingService: FirebaseMessagingService = mock(autoUnit)

    private lateinit var underTest: RemoteNotificationsSubscribeUseCaseImpl

    private fun initUnderTest() {
        underTest = RemoteNotificationsSubscribeUseCaseImpl(
            notificationRepository = mockNotificationRepository,
            firebaseMessagingService = mockFirebaseMessagingService
        )
    }

    @Test
    fun `invoke subscribes to topic via firebase service`() {
        val topic = "test-topic"
        every { mockNotificationRepository.remoteNotificationTopics } returns emptySet()
        everySuspend { mockFirebaseMessagingService.subscribeToTopic(topic) } returns true

        initUnderTest()
        runBlocking { underTest(topic) }

        verifySuspend {
            mockFirebaseMessagingService.subscribeToTopic(topic)
        }
    }

    @Test
    fun `invoke adds topic to repository`() {
        val topic = "test-topic"
        val existingTopics = setOf("existing-topic")
        every { mockNotificationRepository.remoteNotificationTopics } returns existingTopics
        everySuspend { mockFirebaseMessagingService.subscribeToTopic(topic) } returns true

        initUnderTest()
        runBlocking { underTest(topic) }

        verify {
            mockNotificationRepository.remoteNotificationTopics = existingTopics + topic
        }
    }

    @Test
    fun `invoke returns true on success`() {
        val topic = "test-topic"
        every { mockNotificationRepository.remoteNotificationTopics } returns emptySet()
        everySuspend { mockFirebaseMessagingService.subscribeToTopic(topic) } returns true

        initUnderTest()
        val result = runBlocking { underTest(topic) }

        assertTrue(result)
    }

    @Test
    fun `invoke adds topic to existing topics set`() {
        val topic = "new-topic"
        val existingTopics = setOf("topic1", "topic2")
        every { mockNotificationRepository.remoteNotificationTopics } returns existingTopics
        everySuspend { mockFirebaseMessagingService.subscribeToTopic(topic) } returns true

        initUnderTest()
        runBlocking { underTest(topic) }

        verify {
            mockNotificationRepository.remoteNotificationTopics = existingTopics + topic
        }
    }
}
