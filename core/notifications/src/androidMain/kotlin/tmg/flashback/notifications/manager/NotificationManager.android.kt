package tmg.flashback.notifications.manager

import androidx.compose.ui.graphics.Color
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import tmg.flashback.core.notifications.R

internal actual fun createAlarmeePlatformConfiguration(): AlarmeePlatformConfiguration =
    AlarmeeAndroidPlatformConfiguration(
        notificationIconResId = R.drawable.ic_notification,
        notificationIconColor = Color(0xFF01A5D9),
        notificationChannels = emptyList()
    )