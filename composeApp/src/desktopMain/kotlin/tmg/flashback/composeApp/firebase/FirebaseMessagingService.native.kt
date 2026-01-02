package tmg.flashback.composeApp.firebase

import tmg.flashback.notifications.firebase.FirebaseMessagingService

internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {

    actual override suspend fun subscribeToTopic(topicId: String): Boolean {
        return false
    }

    actual override suspend fun unsubscribeFromTopic(topicId: String): Boolean {
        return false
    }
}