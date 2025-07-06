package tmg.flashback

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_channel_free_practice
import flashback.presentation.localisation.generated.resources.notification_channel_info
import flashback.presentation.localisation.generated.resources.notification_channel_qualifying
import flashback.presentation.localisation.generated.resources.notification_channel_qualifying_notify
import flashback.presentation.localisation.generated.resources.notification_channel_race
import flashback.presentation.localisation.generated.resources.notification_channel_race_notify
import flashback.presentation.localisation.generated.resources.notification_channel_sprint
import flashback.presentation.localisation.generated.resources.notification_channel_sprint_notify
import flashback.presentation.localisation.generated.resources.notification_channel_sprint_qualifying
import flashback.presentation.localisation.generated.resources.notification_channel_sprint_qualifying_notify
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.getString
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.notifications.repositories.NotificationRepository

class FlashbackAndroidStartup(
    private val notificationRepository: NotificationRepository
) {

    fun startup(application: FlashbackApplication) {
        // Android specific notification channels
        //  Rest of it is handled by [NotificationManager]
        application.setupNotifications(notificationRepository)
    }

    private fun Application.setupNotifications(
        notificationRepository: NotificationRepository
    ) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //region Legacy: Remove these existing channels which were previously used for remote notifications
        notificationManager.deleteNotificationChannel("race")
        notificationManager.deleteNotificationChannel("qualifying")
        //endregion

        //region Legacy: Unschedule existing notification ids as we shift to guids
        notificationRepository.notificationIds.forEach {
            notificationManager.cancel(it)
        }
        notificationRepository.notificationIds = emptySet()
        //endregion

        // Upcoming
        val upcomingGroupId = "upcoming"
        val upcomingGroup = NotificationChannelGroup(upcomingGroupId, "Upcoming")
        notificationManager.createNotificationChannelGroup(upcomingGroup)
        NotificationUpcoming.entries
            .filter { it != NotificationUpcoming.OTHER }
            .map { NotificationChannel(it.channelId, it.labelId(), NotificationManager.IMPORTANCE_HIGH) }
            .forEach {
                it.group = upcomingGroupId
                notificationManager.createNotificationChannel(it)
            }

        // Upcoming
        val resultsGroupId = "results"
        val resultsGroup = NotificationChannelGroup(resultsGroupId, "Results Available")
        notificationManager.createNotificationChannelGroup(resultsGroup)
        NotificationResultsAvailable.entries
            .map { NotificationChannel(it.channelId, it.labelId(), NotificationManager.IMPORTANCE_HIGH) }
            .forEach {
                it.group = resultsGroupId
                notificationManager.createNotificationChannel(it)
            }
    }

    private fun NotificationUpcoming.labelId(): String {
        return when (this) {
            NotificationUpcoming.RACE -> runBlocking { getString(string.notification_channel_race) }
            NotificationUpcoming.SPRINT -> runBlocking { getString(string.notification_channel_sprint) }
            NotificationUpcoming.SPRINT_QUALIFYING -> runBlocking { getString(string.notification_channel_sprint_qualifying) }
            NotificationUpcoming.QUALIFYING -> runBlocking { getString(string.notification_channel_qualifying) }
            NotificationUpcoming.FREE_PRACTICE -> runBlocking { getString(string.notification_channel_free_practice) }
            NotificationUpcoming.OTHER -> runBlocking { getString(string.notification_channel_info) }
        }
    }
    private fun NotificationResultsAvailable.labelId(): String {
        return when (this) {
            NotificationResultsAvailable.RACE -> runBlocking { getString(string.notification_channel_race_notify) }
            NotificationResultsAvailable.SPRINT -> runBlocking { getString(string.notification_channel_sprint_notify) }
            NotificationResultsAvailable.SPRINT_QUALIFYING -> runBlocking { getString(string.notification_channel_sprint_qualifying_notify) }
            NotificationResultsAvailable.QUALIFYING -> runBlocking { getString(string.notification_channel_qualifying_notify) }
        }
    }
}