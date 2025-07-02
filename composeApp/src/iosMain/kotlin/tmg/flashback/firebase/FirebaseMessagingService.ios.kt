package tmg.flashback.firebase

import cocoapods.FirebaseMessaging.FIRMessaging
import kotlinx.cinterop.ExperimentalForeignApi
import tmg.flashback.notifications.firebase.FirebaseMessagingService

@OptIn(ExperimentalForeignApi::class)
internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String) {
        FIRMessaging.messaging().subscribeToTopic(topicId)
    }

    actual override suspend fun unsubscribeFromTopic(topicId: String) {
        FIRMessaging.messaging().unsubscribeFromTopic(topicId)
    }
}