package tmg.flashback.notifications.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.notifications.receivers.LocalNotificationBroadcastReceiver

actual class NotificationManagerImpl actual constructor(): NotificationManager, KoinComponent {

    private val applicationContext: Context
        get() = KoinJavaComponent.getKoin().get<Context>()

    private val systemAlarmManager: SystemAlarmManager
        get() = KoinJavaComponent.getKoin().get<SystemAlarmManager>()

    private val alarmManager: AlarmManager? by lazy {
        applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    actual override fun schedule(
        uuid: Int,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    ) {
        systemAlarmManager.schedule(
            requestCode = uuid.hashCode(),
            channelId = channelId,
            requestText = title,
            requestDescription = text,
            requestTimestamp = timestamp
        )
    }

    actual override fun cancel(uuid: Int) {
        val alarmManager: AlarmManager = alarmManager ?: return
        val pendingIntent = pendingIntent(applicationContext, "", uuid, "", "")
        alarmManager.cancel(pendingIntent)
    }

    actual override val canScheduleExact: Boolean
        get() {
            val alarmManager: AlarmManager = alarmManager ?: return false
            val result = AlarmManagerCompat.canScheduleExactAlarms(alarmManager)
            logDebug("Notifications", "canScheduleExactAlarms() $result")
            return result
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