package tmg.flashback.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await
import tmg.flashback.notifications.firebase.FirebaseMessagingService
import kotlin.coroutines.suspendCoroutine

internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String) {
        Firebase.messaging.subscribeToTopic(topicId).await()
    }

    actual override suspend fun unsubscribeFromTopic(topicId: String) {
        Firebase.messaging.unsubscribeFromTopic(topicId).await()
    }
}