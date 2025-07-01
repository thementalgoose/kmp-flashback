package tmg.flashback.notifications.di

import org.koin.dsl.module
import tmg.flashback.notifications.repositories.NotificationRepository
import tmg.flashback.notifications.repositories.NotificationRepositoryImpl
import tmg.flashback.notifications.usecases.LocalNotificationsCancelUseCase
import tmg.flashback.notifications.usecases.LocalNotificationsCancelUseCaseImpl
import tmg.flashback.notifications.usecases.LocalNotificationsScheduleUseCase
import tmg.flashback.notifications.usecases.LocalNotificationsScheduleUseCaseImpl
import tmg.flashback.notifications.usecases.RemoteNotificationsSubscribeUseCase
import tmg.flashback.notifications.usecases.RemoteNotificationsSubscribeUseCaseImpl
import tmg.flashback.notifications.usecases.RemoteNotificationsUnsubscribeUseCase
import tmg.flashback.notifications.usecases.RemoteNotificationsUnsubscribeUseCaseImpl

val coreNotificationsModule = listOf(module(), platformModule())

internal fun module() = module {
    single<NotificationRepository> { NotificationRepositoryImpl(get(), get()) }

    single<LocalNotificationsScheduleUseCase> { LocalNotificationsScheduleUseCaseImpl(get()) }
    single<LocalNotificationsCancelUseCase> { LocalNotificationsCancelUseCaseImpl(get(), get()) }
    single<RemoteNotificationsUnsubscribeUseCase> { RemoteNotificationsUnsubscribeUseCaseImpl(get()) }
    single<RemoteNotificationsSubscribeUseCase> { RemoteNotificationsSubscribeUseCaseImpl(get()) }
}