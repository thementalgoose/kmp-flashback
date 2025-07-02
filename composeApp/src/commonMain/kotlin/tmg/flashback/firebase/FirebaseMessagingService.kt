package tmg.flashback.firebase

import tmg.flashback.notifications.firebase.FirebaseMessagingService

internal expect class FirebaseMessagingServiceImpl(): FirebaseMessagingService {
    override suspend fun subscribeToTopic(topicId: String)
    override suspend fun unsubscribeFromTopic(topicId: String)
}