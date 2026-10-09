package tmg.flashback.feature.notifications.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.notifications.presentation.NotificationPromptViewModel
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepositoryImpl
import tmg.flashback.feature.notifications.usecases.GetNotificationResultsAvailableStateUseCase
import tmg.flashback.feature.notifications.usecases.GetNotificationResultsAvailableStateUseCaseImpl
import tmg.flashback.feature.notifications.usecases.GetNotificationUpcomingStateUseCase
import tmg.flashback.feature.notifications.usecases.GetNotificationUpcomingStateUseCaseImpl
import tmg.flashback.feature.notifications.usecases.IsNotificationEnabledUseCase
import tmg.flashback.feature.notifications.usecases.IsNotificationEnabledUseCaseImpl
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCaseImpl
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCaseImpl

val featureNotificationsModule = listOf(module())

internal fun module() = module {
    single<NotificationSettingsRepository> { NotificationSettingsRepositoryImpl(get()) }

    single<SubscribeResultNotificationsUseCase> { SubscribeResultNotificationsUseCaseImpl(get(), get(), get(), get()) }
    single<ScheduleUpcomingNotificationsUseCase> { ScheduleUpcomingNotificationsUseCaseImpl(get(), get(), get(), get(), get(), get()) }

    single<IsNotificationEnabledUseCase> { IsNotificationEnabledUseCaseImpl(get(), get()) }
    single<GetNotificationUpcomingStateUseCase> { GetNotificationUpcomingStateUseCaseImpl(get()) }
    single<GetNotificationResultsAvailableStateUseCase> { GetNotificationResultsAvailableStateUseCaseImpl(get()) }

    viewModel { NotificationPromptViewModel(get(), get(), get(), get(), get()) }
}
