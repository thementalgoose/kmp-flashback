package tmg.flashback.composeApp

import org.koin.core.annotation.Single
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase

@Single
class FlashbackIOSStartup(
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase
) {
    fun startup() {

    }

    suspend fun subscribeNotifications() {
        subscribeResultNotificationsUseCase()
    }
}