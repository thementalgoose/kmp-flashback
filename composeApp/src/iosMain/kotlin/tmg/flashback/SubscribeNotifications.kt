package tmg.flashback

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase

/**
 * Called from the iOS App Delegate after a device token is retrieved
 * Attempting to register topics between application.registerForRemoteNotifications and
 *  didReceiveRegistrationToken will fail to subscribe
 */
fun doSubscribe() {
    GlobalScope.launch {
        val subscribeResultNotificationsUseCase = KoinPlatform.getKoinOrNull()?.get<SubscribeResultNotificationsUseCase>()
        subscribeResultNotificationsUseCase?.invoke()
    }
}