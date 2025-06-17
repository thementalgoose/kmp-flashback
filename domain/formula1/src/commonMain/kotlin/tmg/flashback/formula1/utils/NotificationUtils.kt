package tmg.flashback.formula1.utils

import kotlinx.datetime.LocalDateTime
import tmg.flashback.formula1.enums.RaceWeekend

object NotificationUtils {

    /**
     * Generate a unique request code per timestamp
     */
    fun getRequestCode(date: LocalDateTime): Int {
        return 1
        // TODO
//        return date
//            .toEpochSecond(ZoneOffset.UTC)
//            .coerceIn(0, Int.MAX_VALUE.toLong())
//            .toInt()
    }

    /**
     * Get the content of the notification
     * - Race starts in 30 minutes
     * - Russian Grand Prix Race starts in 30 minutes at 12:30 Europe/London (device time)
     */
//    fun getNotificationTitleText(context: Context, title: String, label: String, timestamp: Timestamp, notificationReminder: NotificationReminder): Pair<String, String> {
//        val reminderString = context.getString(notificationReminder.label)
//        val notificationTitle = context.getString(R.string.notification_content_title, label, reminderString)
//
//        val deviceDateTime: LocalDateTime = timestamp.deviceLocalDateTime
//
//        val timeString = deviceDateTime.format(DateTimeFormatter.ofPattern("HH:mm", Locale.UK))
//        val deviceTimeString = context.getString(R.string.notification_content_text_device_time, timeString, ZoneId.systemDefault().id)
//        val notificationText = context.getString(R.string.notification_content_text, title, label, reminderString, deviceTimeString)
//
//        return Pair(notificationTitle, notificationText)
//    }

    /**
     * Get the content of the notification
     * - Race starts in 30 minutes
     * - Russian Grand Prix Race starts at 12:30 Europe/London (device time)
     */
//    fun getInexactNotificationTitleText(context: Context, title: String, label: String, timestamp: Timestamp): Pair<String, String> {
//        val deviceDateTime: LocalDateTime = timestamp.deviceLocalDateTime
//        val timeString = deviceDateTime.format(DateTimeFormatter.ofPattern("HH:mm", Locale.UK))
//
//        val notificationTitle = context.getString(R.string.notification_content_title_inexact, label, timeString)
//        val deviceTimeString = context.getString(R.string.notification_content_text_device_time, timeString, ZoneId.systemDefault().id)
//        val notificationText = context.getString(R.string.notification_content_text_inexact, title, label, deviceTimeString)
//
//        return Pair(notificationTitle, notificationText)
//    }

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