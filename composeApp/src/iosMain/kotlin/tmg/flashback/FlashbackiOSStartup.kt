package tmg.flashback

import dev.icerock.moko.permissions.ios.PermissionsController
import tmg.flashback.notifications.repositories.NotificationRepository
import tmg.flashback.ui.permissions.PermissionManager

class FlashbackIOSStartup(
    private val permissionManager: PermissionManager
){
    fun startup() {
        permissionManager.permissionsController = PermissionsController()
    }
}