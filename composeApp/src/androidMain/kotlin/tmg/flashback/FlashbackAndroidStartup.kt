package tmg.flashback

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import tmg.flashback.formula1.model.notifications.NotificationResultsAvailable
import tmg.flashback.formula1.model.notifications.NotificationUpcoming
import tmg.flashback.notifications.repositories.NotificationRepository

class FlashbackAndroidStartup {

    fun startup(
        application: FlashbackApplication,
        notificationRepository: NotificationRepository
    ) {
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
            .map { NotificationChannel(it.channelId, getString(it.labelId), NotificationManager.IMPORTANCE_HIGH) }
            .forEach {
                it.group = upcomingGroupId
                notificationManager.createNotificationChannel(it)
            }

        // Upcoming
        val resultsGroupId = "results"
        val resultsGroup = NotificationChannelGroup(resultsGroupId, "Results Available")
        notificationManager.createNotificationChannelGroup(resultsGroup)
        NotificationResultsAvailable.entries
            .map { NotificationChannel(it.channelId, getString(it.labelId), NotificationManager.IMPORTANCE_HIGH) }
            .forEach {
                it.group = upcomingGroupId
                notificationManager.createNotificationChannel(it)
            }
    }

    private val NotificationUpcoming.labelId: Int
        get() = when (this) {
            NotificationUpcoming.RACE -> R.string.app_name
            NotificationUpcoming.SPRINT -> R.string.app_name
            NotificationUpcoming.SPRINT_QUALIFYING -> R.string.app_name
            NotificationUpcoming.QUALIFYING -> R.string.app_name
            NotificationUpcoming.FREE_PRACTICE -> R.string.app_name
            NotificationUpcoming.OTHER -> R.string.app_name
        }
    private val NotificationResultsAvailable.labelId: Int
        get() = when (this) {
            NotificationResultsAvailable.RACE -> R.string.app_name
            NotificationResultsAvailable.SPRINT -> R.string.app_name
            NotificationResultsAvailable.SPRINT_QUALIFYING -> R.string.app_name
            NotificationResultsAvailable.QUALIFYING -> R.string.app_name
        }
}