package tmg.flashback.composeApp

import org.koin.core.annotation.Single
import tmg.flashback.feature.notifications.usecases.SubscribeNewsNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase

@Single
class FlashbackIOSStartup(
    private val subscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase,
    private val subscribeNewsNotificationsUseCase: SubscribeNewsNotificationsUseCase
) {
    fun startup() {

    }

    suspend fun subscribeNotifications() {
        subscribeResultNotificationsUseCase()
        subscribeNewsNotificationsUseCase()
    }
}