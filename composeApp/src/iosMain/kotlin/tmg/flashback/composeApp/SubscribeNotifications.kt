package tmg.flashback.composeApp

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform

/**
 * Called from the iOS App Delegate after a device token is retrieved
 * Attempting to register topics between application.registerForRemoteNotifications and
 *  didReceiveRegistrationToken will fail to subscribe
 */
fun doSubscribe() {
    GlobalScope.launch {
        val startup = KoinPlatform.getKoinOrNull()?.get<FlashbackIOSStartup>()
        startup?.subscribeNotifications()
    }
}