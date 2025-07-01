package tmg.flashback.notifications.manager

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.alarmee.createAlarmeeService
import com.tweener.alarmee.model.Alarmee
import com.tweener.alarmee.model.AndroidNotificationConfiguration
import com.tweener.alarmee.model.AndroidNotificationPriority
import com.tweener.alarmee.model.IosNotificationConfiguration
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import tmg.flashback.notifications.repositories.NotificationRepository

interface NotificationManager {
    fun schedule(
        uuid: String,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    )

    fun cancel(
        uuid: String
    )
}

internal class NotificationManagerImpl(
    private val notificationRepository: NotificationRepository
): NotificationManager {
    val service = createAlarmeeService().apply {
        initialize(createAlarmeePlatformConfiguration())
    }

    override fun schedule(
        uuid: String,
        channelId: String,
        title: String,
        text: String,
        timestamp: LocalDateTime
    ) {
        val alarmee = Alarmee(
            uuid = uuid,
            notificationTitle = title,
            notificationBody = text,
            scheduledDateTime = timestamp,
            timeZone = TimeZone.currentSystemDefault(),
            repeatInterval = null,
            androidNotificationConfiguration = AndroidNotificationConfiguration(
                priority = AndroidNotificationPriority.HIGH,
                channelId = channelId,
                iconResId = null,
                iconColor = null
            ),
            iosNotificationConfiguration = IosNotificationConfiguration()
        )
        notificationRepository.notificationUuids += uuid
        service.local.schedule(alarmee)
    }

    override fun cancel(uuid: String) {
        notificationRepository.notificationUuids -= uuid
        service.local.cancel(uuid)
    }
}

internal expect fun createAlarmeePlatformConfiguration(): AlarmeePlatformConfiguration
