package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.notifications.firebase.FirebaseMessagingService

@Single
internal expect class FirebaseMessagingServiceImpl(): FirebaseMessagingService {
    override suspend fun subscribeToTopic(topicId: String): Boolean
    override suspend fun unsubscribeFromTopic(topicId: String): Boolean
}