package tmg.flashback.firebase

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.tasks.await
import tmg.flashback.notifications.firebase.FirebaseMessagingService

internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String) {
        Firebase.messaging.subscribeToTopic(topicId).await()
    }

    actual override suspend fun unsubscribeFromTopic(topicId: String) {
        Firebase.messaging.unsubscribeFromTopic(topicId).await()
    }
}