package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.notifications.firebase.FirebaseMessagingService

@Single(binds = [FirebaseMessagingService::class])
internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String): Boolean = false
    actual override suspend fun unsubscribeFromTopic(topicId: String): Boolean = false
}