package tmg.flashback.firebase

import tmg.flashback.notifications.firebase.FirebaseMessagingService

internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String) {

    }

    actual override suspend fun unsubscribeFromTopic(topicId: String) {

    }
}