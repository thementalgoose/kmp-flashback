package tmg.flashback.notifications.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class NotificationRepositoryTest {

    private lateinit var underTest: NotificationRepositoryImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = NotificationRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    //region Remote Notification Token

    @Test
    fun `save remote notification token saves under key`() {
        initUnderTest()
        underTest.remoteNotificationToken = "test-token"
        verify {
            mockPreferenceManager.save(expectedKeyRemoteToken, "test-token")
        }
    }

    @Test
    fun `save null remote notification token saves empty string`() {
        initUnderTest()
        underTest.remoteNotificationToken = null
        verify {
            mockPreferenceManager.save(expectedKeyRemoteToken, "")
        }
    }

    @Test
    fun `reading remote notification token reads value`() {
        initUnderTest()
        every { mockPreferenceManager.getString(expectedKeyRemoteToken, null) } returns "test-token"
        assertEquals("test-token", underTest.remoteNotificationToken)
    }

    @Test
    fun `reading remote notification token returns null when not set`() {
        initUnderTest()
        every { mockPreferenceManager.getString(expectedKeyRemoteToken, null) } returns null
        assertNull(underTest.remoteNotificationToken)
    }

    //endregion

    //region Remote Notification Topics

    @Test
    fun `save remote notification topics saves under key`() {
        initUnderTest()
        val topics = mutableSetOf("topic1", "topic2")
        underTest.remoteNotificationTopics = topics
        verify {
            mockPreferenceManager.save(expectedKeyRemoteTopics, topics)
        }
    }

    @Test
    fun `reading remote notification topics reads value`() {
        initUnderTest()
        val topics = mutableSetOf("topic1", "topic2")
        every { mockPreferenceManager.getSet(expectedKeyRemoteTopics, emptySet()) } returns topics
        assertEquals(topics, underTest.remoteNotificationTopics)
    }

    @Test
    fun `reading remote notification topics returns empty set when not set`() {
        initUnderTest()
        every { mockPreferenceManager.getSet(expectedKeyRemoteTopics, emptySet()) } returns mutableSetOf()
        assertTrue(underTest.remoteNotificationTopics.isEmpty())
    }

    //endregion

    //region Notification IDs

    @Test
    fun `save notification ids saves under key as strings`() {
        initUnderTest()
        val ids = mutableSetOf(1, 2, 3)
        underTest.notificationIds = ids
        verify {
            mockPreferenceManager.save(expectedKeyNotificationIds, mutableSetOf("1", "2", "3"))
        }
    }

    @Test
    fun `reading notification ids reads and converts to integers`() {
        initUnderTest()
        every { mockPreferenceManager.getSet(expectedKeyNotificationIds, emptySet()) } returns mutableSetOf("1", "2", "3")
        assertEquals(mutableSetOf(1, 2, 3), underTest.notificationIds)
    }

    @Test
    fun `reading notification ids filters out invalid integers`() {
        initUnderTest()
        every { mockPreferenceManager.getSet(expectedKeyNotificationIds, emptySet()) } returns mutableSetOf("1", "invalid", "3")
        assertEquals(mutableSetOf(1, 3), underTest.notificationIds)
    }

    @Test
    fun `reading notification ids returns empty set when not set`() {
        initUnderTest()
        every { mockPreferenceManager.getSet(expectedKeyNotificationIds, emptySet()) } returns mutableSetOf()
        assertTrue(underTest.notificationIds.isEmpty())
    }

    //endregion

    companion object {
        private const val expectedKeyRemoteToken: String = "NOTIFICATION_REMOTE_TOKEN"
        private const val expectedKeyRemoteTopics: String = "NOTIFICATION_REMOTE_TOPICS"
        private const val expectedKeyNotificationIds: String = "NOTIFICATION_IDS"
    }
}
