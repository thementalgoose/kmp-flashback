package tmg.flashback.notifications.firebase

interface FirebaseMessagingService {
    suspend fun subscribeToTopic(topicId: String): Boolean
    suspend fun unsubscribeFromTopic(topicId: String): Boolean
}