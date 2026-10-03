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

internal class RemoteNotificationsUnsubscribeUseCaseTest {

    private val mockNotificationRepository: NotificationRepository = mock(autoUnit)
    private val mockFirebaseMessagingService: FirebaseMessagingService = mock(autoUnit)

    private lateinit var underTest: RemoteNotificationsUnsubscribeUseCaseImpl

    private fun initUnderTest() {
        underTest = RemoteNotificationsUnsubscribeUseCaseImpl(
            notificationRepository = mockNotificationRepository,
            firebaseMessagingService = mockFirebaseMessagingService
        )
    }

    @Test
    fun `invoke unsubscribes from topic via firebase service`() {
        val topic = "test-topic"
        every { mockNotificationRepository.remoteNotificationTopics } returns setOf(topic)
        everySuspend { mockFirebaseMessagingService.unsubscribeFromTopic(topic) } returns true

        initUnderTest()
        runBlocking { underTest(topic) }

        verifySuspend {
            mockFirebaseMessagingService.unsubscribeFromTopic(topic)
        }
    }

    @Test
    fun `invoke removes topic from repository`() {
        val topic = "test-topic"
        val existingTopics = setOf("test-topic", "other-topic")
        every { mockNotificationRepository.remoteNotificationTopics } returns existingTopics
        everySuspend { mockFirebaseMessagingService.unsubscribeFromTopic(topic) } returns true

        initUnderTest()
        runBlocking { underTest(topic) }

        verify {
            mockNotificationRepository.remoteNotificationTopics = existingTopics - topic
        }
    }

    @Test
    fun `invoke returns true on success`() {
        val topic = "test-topic"
        every { mockNotificationRepository.remoteNotificationTopics } returns setOf(topic)
        everySuspend { mockFirebaseMessagingService.unsubscribeFromTopic(topic) } returns true

        initUnderTest()
        val result = runBlocking { underTest(topic) }

        assertTrue(result)
    }

    @Test
    fun `invoke removes only specified topic from multiple topics`() {
        val topicToRemove = "topic2"
        val existingTopics = setOf("topic1", "topic2", "topic3")
        every { mockNotificationRepository.remoteNotificationTopics } returns existingTopics
        everySuspend { mockFirebaseMessagingService.unsubscribeFromTopic(topicToRemove) } returns true

        initUnderTest()
        runBlocking { underTest(topicToRemove) }

        verify {
            mockNotificationRepository.remoteNotificationTopics = setOf("topic1", "topic3")
        }
    }

    @Test
    fun `invoke handles removing non-existent topic`() {
        val topic = "non-existent-topic"
        val existingTopics = setOf("topic1", "topic2")
        every { mockNotificationRepository.remoteNotificationTopics } returns existingTopics
        everySuspend { mockFirebaseMessagingService.unsubscribeFromTopic(topic) } returns true

        initUnderTest()
        runBlocking { underTest(topic) }

        verify {
            mockNotificationRepository.remoteNotificationTopics = existingTopics - topic
        }
    }
}
