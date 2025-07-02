package tmg.flashback.notifications.firebase

interface FirebaseMessagingService {
    suspend fun subscribeToTopic(topicId: String)
    suspend fun unsubscribeFromTopic(topicId: String)
}