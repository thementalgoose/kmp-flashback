package tmg.flashback.notifications.manager

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import platform.Foundation.NSDateComponents
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import tmg.flashback.infrastructure.log.logInfo
import kotlin.time.Clock

actual class NotificationManagerImpl actual constructor(): NotificationManager {

    actual override fun schedule(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    ) {
        val content = UNMutableNotificationContent()
        content.setTitle(title)
        content.setBody(text)

        val instant = timestamp.toInstant(TimeZone.UTC)
        val nowMillis = Clock.System.now().toEpochMilliseconds()
        val timeInterval = (instant.toEpochMilliseconds() - nowMillis) / 1000
        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(timeInterval = timeInterval.toDouble(), repeats = false)

        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = uuid.toString(),
            content = content,
            trigger = trigger
        )

        logInfo("Notification", "Scheduling notification for ${instant.toEpochMilliseconds()} (current system is ${nowMillis}, with seconds diff being $timeInterval seconds) - $title")

        UNUserNotificationCenter
            .currentNotificationCenter()
            .addNotificationRequest(request, null)
    }

    actual override fun cancel(uuid: Int) {
        UNUserNotificationCenter
            .currentNotificationCenter()
            .removePendingNotificationRequestsWithIdentifiers(listOf(uuid.toString()))
    }

    actual override val canScheduleExact: Boolean
        get() = true
}