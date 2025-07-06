package tmg.flashback.notifications.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import tmg.flashback.core.notifications.R
import tmg.flashback.infrastructure.device.Device

object NotificationBuilder {

    /**
     * Build a notification object to be displayed to the user
     *
     * @param context Defaults to application context but should be supplied from the broadcast receiver
     */
    fun buildNotification(
        context: Context,
        channelId: String,
        title: String,
        text: String,
        @DrawableRes
        icon: Int = R.drawable.ic_notification,
        expireAfterMillis: Long = 1000 * 60 * 60 * 3
    ): Notification {
        val intent = context.packageManager.getLaunchIntentForPackage(Device.applicationId)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0 /* Request code */,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultAlarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentTitle(title)
            .setContentText(text)
            .setSound(defaultAlarmSound)
            .setTimeoutAfter(expireAfterMillis)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        return notificationBuilder.build()
    }
}