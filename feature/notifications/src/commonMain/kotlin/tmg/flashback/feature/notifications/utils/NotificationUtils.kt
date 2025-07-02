package tmg.flashback.feature.notifications.utils

import flashback.presentation.localisation.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_content_text
import flashback.presentation.localisation.generated.resources.notification_content_text_device_time
import flashback.presentation.localisation.generated.resources.notification_content_text_inexact
import flashback.presentation.localisation.generated.resources.notification_content_title
import flashback.presentation.localisation.generated.resources.notification_content_title_inexact
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import org.jetbrains.compose.resources.getString
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.formula1.enums.RaceWeekend
import tmg.flashback.formula1.model.Timestamp
import tmg.flashback.infrastructure.datetime.timeFormatHHmm

object NotificationUtils {

    /**
     * Get the content of the notification
     * - Race starts in 30 minutes
     * - Russian Grand Prix Race starts in 30 minutes at 12:30 Europe/London (device time)
     */
    suspend fun getNotificationTitleText(title: String, label: String, timestamp: Timestamp, notificationReminder: NotificationReminder): Pair<String, String> {
        val reminderString = notificationReminder.label
        val notificationTitle = getString(string.notification_content_title, label, reminderString)

        val deviceDateTime: LocalDateTime = timestamp.deviceLocalDateTime

        val timeString = deviceDateTime.time.format(timeFormatHHmm)
        val deviceTimeString = getString(string.notification_content_text_device_time, timeString, TimeZone.currentSystemDefault().id)
        val notificationText = getString(string.notification_content_text, title, label, reminderString, deviceTimeString)

        return Pair(notificationTitle, notificationText)
    }

    /**
     * Get the content of the notification
     * - Race starts in 30 minutes
     * - Russian Grand Prix Race starts at 12:30 Europe/London (device time)
     */
    suspend fun getInexactNotificationTitleText(title: String, label: String, timestamp: Timestamp): Pair<String, String> {
        val deviceDateTime: LocalDateTime = timestamp.deviceLocalDateTime
        val timeString = deviceDateTime.time.format(timeFormatHHmm)

        val notificationTitle = getString(string.notification_content_title_inexact, label, timeString)
        val deviceTimeString = getString(string.notification_content_text_device_time, timeString, TimeZone.currentSystemDefault().id)
        val notificationText = getString(string.notification_content_text_inexact, title, label, deviceTimeString)

        return Pair(notificationTitle, notificationText)
    }

    /**
     * Get which category the notification should be grouped in to based on the label of the event
     */
    fun getCategoryBasedOnLabel(label: String): RaceWeekend? {
        return when {
            label.includes(
                "shootout",
                "sprint shootout"
            ) -> RaceWeekend.SPRINT_QUALIFYING
            label.includes(
                "sprint",
                "sprint qualifying",
                "sprint quali",
            ) -> RaceWeekend.SPRINT
            label.includes(
                "qualifying",
                "quali"
            ) -> RaceWeekend.QUALIFYING
            label.includes(
                "race",
                "grand prix",
            ) -> RaceWeekend.RACE
            label.includes(
                "fp",
                "free practice",
                "practice"
            ) -> RaceWeekend.FREE_PRACTICE
            else -> null
        }
    }

    private fun String.includes(vararg partials: String): Boolean {
        return partials.any { label ->
            this.lowercase().contains(label.lowercase())
        }
    }
}