package tmg.flashback.notifications.manager

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfigurationNonMobile

internal actual fun createAlarmeePlatformConfiguration(): AlarmeePlatformConfiguration =
    AlarmeePlatformConfigurationNonMobile()