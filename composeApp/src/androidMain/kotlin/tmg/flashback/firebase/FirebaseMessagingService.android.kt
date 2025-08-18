package tmg.flashback.firebase

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.tasks.await
import tmg.flashback.notifications.firebase.FirebaseMessagingService

internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String): Boolean {
        Firebase.messaging.subscribeToTopic(topicId).await()
        return true
    }

    actual override suspend fun unsubscribeFromTopic(topicId: String): Boolean {
        Firebase.messaging.unsubscribeFromTopic(topicId).await()
        return true
    }
}