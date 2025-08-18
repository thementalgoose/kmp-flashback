package tmg.flashback.firebase

import cocoapods.FirebaseMessaging.FIRMessaging
import kotlinx.cinterop.ExperimentalForeignApi
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.notifications.firebase.FirebaseMessagingService
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String) = suspendCoroutine { continuation ->
        logInfo("Firebase", "Subscribing to notification topic '$topicId'...")
        FIRMessaging.messaging().subscribeToTopic(topic = topicId) { error ->
            logInfo("Firebase", "Subscribed to notification topic '$topicId' ($error)")
            continuation.resumeWith(Result.success(error == null))
        }
    }

    actual override suspend fun unsubscribeFromTopic(topicId: String) = suspendCoroutine { continuation ->
        logInfo("Firebase", "Unsubscribing from notification topic '$topicId'...")
        FIRMessaging.messaging().unsubscribeFromTopic(topicId) { error ->
            logInfo("Firebase", "Unsubscribed from notification topic '$topicId' ($error)")
            continuation.resumeWith(Result.success(error == null))
        }
    }
}