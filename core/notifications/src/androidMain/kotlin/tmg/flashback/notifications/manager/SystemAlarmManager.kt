package tmg.flashback.notifications.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.java.KoinJavaComponent
import tmg.flashback.notifications.receivers.LocalNotificationBroadcastReceiver

class SystemAlarmManager {

    private val applicationContext: Context
        get() = KoinJavaComponent.getKoin().get<Context>()

    private val alarmManager: AlarmManager? by lazy {
        applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    /**
     * Schedule a pending intent with a title and description
     */
    fun schedule(
        requestCode: Int,
        channelId: String,
        requestText: String,
        requestDescription: String,
        requestTimestamp: LocalDateTime,
        exact: Boolean
    ) {
        val alarmManager: AlarmManager = alarmManager ?: return
        val pendingIntent = pendingIntent(applicationContext, channelId, requestCode, requestText, requestDescription)

        val instant = requestTimestamp.toInstant(TimeZone.UTC)
        Log.d("Notification", "Scheduling (exact=$exact) alarm wakeup for ${instant.toEpochMilliseconds()} (current system is ${System.currentTimeMillis()}, with millis diff being ${(instant.toEpochMilliseconds() - System.currentTimeMillis()) / 1000} seconds) - $requestText")
        when (exact) {
            true -> AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                instant.toEpochMilliseconds(),
                pendingIntent
            )
            false -> AlarmManagerCompat.setAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                instant.toEpochMilliseconds(),
                pendingIntent
            )
        }
    }

    /**
     * Cancel a pending intent via. the request code
     */
    fun cancel(requestCode: Int) {
        val systemAlarmManager: AlarmManager = alarmManager ?: return

        val pendingIntent = pendingIntent(applicationContext, "", requestCode, "", "")
        systemAlarmManager.cancel(pendingIntent)
    }

    private fun pendingIntent(context: Context, channelId: String, requestCode: Int, title: String, description: String): PendingIntent {
        val localNotificationReceiverIntent = LocalNotificationBroadcastReceiver.intent(context,
            channelId = channelId,
            title = title,
            description = description
        )
        return PendingIntent.getBroadcast(applicationContext, requestCode, localNotificationReceiverIntent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
    }
}