package tmg.flashback.feature.notifications.di

import org.koin.dsl.module
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepositoryImpl
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCaseImpl
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCaseImpl

val featureNotificationsModule = listOf(module())

internal fun module() = module {
    single<NotificationSettingsRepository> { NotificationSettingsRepositoryImpl(get()) }

    single<SubscribeResultNotificationsUseCase> { SubscribeResultNotificationsUseCaseImpl(get(), get()) }
    single<ScheduleUpcomingNotificationsUseCase> { ScheduleUpcomingNotificationsUseCaseImpl(get(), get(), get(), get(), get()) }
}