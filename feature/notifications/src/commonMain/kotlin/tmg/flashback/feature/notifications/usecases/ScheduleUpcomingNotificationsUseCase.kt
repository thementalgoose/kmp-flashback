package tmg.flashback.feature.notifications.usecases

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.utils.NotificationUtils
import tmg.flashback.formula1.enums.RaceWeekend
import tmg.flashback.formula1.model.Timestamp
import tmg.flashback.infrastructure.datetime.plus
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.notifications.manager.NotificationManager
import tmg.flashback.notifications.repositories.NotificationRepository
import tmg.flashback.notifications.usecases.LocalNotificationsCancelUseCase
import tmg.flashback.notifications.usecases.LocalNotificationsScheduleUseCase

interface ScheduleUpcomingNotificationsUseCase {
    suspend operator fun invoke(force: Boolean = false): ScheduleResult
}

internal class ScheduleUpcomingNotificationsUseCaseImpl(
    private val overviewRepository: OverviewRepository,
    private val notificationRepository: NotificationRepository,
    private val localNotificationsCancelUseCase: LocalNotificationsCancelUseCase,
    private val localNotificationsScheduleUseCase: LocalNotificationsScheduleUseCase,
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val notificationManager: NotificationManager,
): ScheduleUpcomingNotificationsUseCase {
    override suspend fun invoke(force: Boolean): ScheduleResult {

        val enabledUpcomingNotifications = notificationSettingsRepository.notificationUpcomingEnabled
        val notificationReminder = when (notificationManager.canScheduleExact) {
            true -> notificationSettingsRepository.notificationReminderPeriod
            false -> NotificationReminder.MINUTES_60
        }

        if (enabledUpcomingNotifications.isEmpty()) {
            logInfo("Notifications", "All notifications disabled, skipping")
            return ScheduleResult.Disabled
        }

        val upNextItemsToSchedule = (overviewRepository.getUpcomingOverviews() ?: emptyList())
            .map { event ->
                event.schedule.mapIndexed { index, item ->
                    NotificationModel(
                        season = event.season,
                        round = event.round,
                        value = index,
                        title = event.raceName,
                        label = item.label,
                        timestamp = item.timestamp,
                        uuid = (item.timestamp.deviceLocalDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds() / 1000L).toInt(),
                        utcDateTime = item.timestamp.utcLocalDateTime,
                        channel = item.label.toChannel()
                    )
                }
            }
            .flatten()
            .filter { !it.timestamp.isInPastRelativeToo(notificationReminder.seconds.toLong()) }

        if (upNextItemsToSchedule.isEmpty()) {
            logInfo("Notifications", "Skipping rescheduling of notifications as there are no items to schedule for")
            return ScheduleResult.Unchanged
        }

        if (upNextItemsToSchedule.map { it.uuid }.toSet() == notificationRepository.notificationIds && !force) {
            logInfo("Notifications", "Skipping rescheduling of notifications as it remains unchanged")
            return ScheduleResult.Unchanged
        }

        localNotificationsCancelUseCase.cancelAll()
        val reminderPeriod = notificationReminder

        upNextItemsToSchedule.forEach { item ->
            if (enabledUpcomingNotifications.contains(item.channel)) {
                val time = item.timestamp.utcLocalDateTime.plus(-reminderPeriod.seconds, DateTimeUnit.SECOND, timeZone = TimeZone.UTC)
                val (title, text) = NotificationUtils.getInexactNotificationTitleText(
                    item.title,
                    item.label,
                    item.timestamp
                )

                localNotificationsScheduleUseCase(
                    uuid = item.uuid,
                    channelId = item.channel.channelId,
                    title = title,
                    text = text,
                    timestamp = time,
                )
            }
        }

        return ScheduleResult.Scheduled
    }

    inner class NotificationModel(
        val season: Int,
        val round: Int,
        val value: Int,
        val title: String,
        val label: String,
        val uuid: Int,
        val channel: NotificationUpcoming,
        val timestamp: Timestamp,
        val utcDateTime: LocalDateTime,
    )

    private fun String.toChannel(): NotificationUpcoming {
        return when (NotificationUtils.getCategoryBasedOnLabel(this)) {
            RaceWeekend.FREE_PRACTICE -> NotificationUpcoming.FREE_PRACTICE
            RaceWeekend.QUALIFYING -> NotificationUpcoming.QUALIFYING
            RaceWeekend.SPRINT_QUALIFYING -> NotificationUpcoming.SPRINT_QUALIFYING
            RaceWeekend.SPRINT -> NotificationUpcoming.SPRINT
            RaceWeekend.RACE -> NotificationUpcoming.RACE
            null -> NotificationUpcoming.OTHER
        }
    }
}

sealed interface ScheduleResult {
    data object Unchanged: ScheduleResult
    data object Scheduled: ScheduleResult
    data object Disabled: ScheduleResult
    data object Error: ScheduleResult
}