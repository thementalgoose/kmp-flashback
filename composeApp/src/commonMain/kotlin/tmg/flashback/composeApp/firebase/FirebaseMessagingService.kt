package tmg.flashback.composeApp.firebase

import tmg.flashback.notifications.firebase.FirebaseMessagingService

internal expect class FirebaseMessagingServiceImpl(): FirebaseMessagingService {
    override suspend fun subscribeToTopic(topicId: String): Boolean
    override suspend fun unsubscribeFromTopic(topicId: String): Boolean
}