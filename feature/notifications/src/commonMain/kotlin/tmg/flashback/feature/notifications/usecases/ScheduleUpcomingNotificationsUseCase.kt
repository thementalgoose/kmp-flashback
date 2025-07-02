package tmg.flashback.feature.notifications.usecases

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.utils.NotificationUtils
import tmg.flashback.formula1.enums.RaceWeekend
import tmg.flashback.formula1.model.Timestamp
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.infrastructure.datetime.plus
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.notifications.repositories.NotificationRepository
import tmg.flashback.notifications.usecases.LocalNotificationsCancelUseCase
import tmg.flashback.notifications.usecases.LocalNotificationsScheduleUseCase

interface ScheduleUpcomingNotificationsUseCase {
    suspend operator fun invoke(): ScheduleResult
}

internal class ScheduleUpcomingNotificationsUseCaseImpl(
    private val overviewRepository: OverviewRepository,
    private val notificationRepository: NotificationRepository,
    private val localNotificationsCancelUseCase: LocalNotificationsCancelUseCase,
    private val localNotificationsScheduleUseCase: LocalNotificationsScheduleUseCase,
    private val notificationSettingsRepository: NotificationSettingsRepository,
): ScheduleUpcomingNotificationsUseCase {
    override suspend fun invoke(): ScheduleResult {

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
                        uuid = "${event.season}-${event.round}-${item.label}",
                        utcDateTime = item.timestamp.utcLocalDateTime,
                        channel = item.label.toChannel()
                    )
                }
            }
            .flatten()
            .filter { !it.timestamp.isInPastRelativeToo(notificationSettingsRepository.notificationReminderPeriod.seconds.toLong()) }

        if (upNextItemsToSchedule.map { it.uuid }.toSet() == notificationRepository.notificationUuids) {
            logInfo("Notifications", "Skipping rescheduling of notifications as it remains unchanged")
            return ScheduleResult.Unchanged
        }

        localNotificationsCancelUseCase.cancelAll()
        val reminderPeriod = notificationSettingsRepository.notificationReminderPeriod

        upNextItemsToSchedule.forEach { item ->
            val time = item.utcDateTime.plus(-reminderPeriod.seconds, DateTimeUnit.SECOND, timeZone = TimeZone.UTC)
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

            logInfo("Notification", " s${item.season} r${item.round} [${item.label}] ${item.title} has been scheduled at $time")

        }

        return ScheduleResult.Scheduled
    }

    inner class NotificationModel(
        val season: Int,
        val round: Int,
        val value: Int,
        val title: String,
        val label: String,
        val uuid: String,
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
    data object Error: ScheduleResult
}