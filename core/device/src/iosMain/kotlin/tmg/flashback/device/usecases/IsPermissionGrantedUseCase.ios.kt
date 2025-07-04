package tmg.flashback.device.usecases

import tmg.flashback.device.models.Permission
import tmg.flashback.device.models.PermissionState

import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNNotificationSettings
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.infrastructure.log.logInfo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class IsPermissionGrantedUseCaseImpl actual constructor(): IsPermissionGrantedUseCase {
    actual override suspend fun invoke(permission: Permission): PermissionState {
        return when (permission) {
            Permission.Notifications -> checkNotificationPermission()
        }
    }

    private suspend fun checkNotificationPermission(): PermissionState {
        return suspendCoroutine { continuation ->
            val currentCenter = UNUserNotificationCenter.currentNotificationCenter()
            currentCenter.getNotificationSettingsWithCompletionHandler { settings: UNNotificationSettings? ->
                if (settings == null) {
                    logDebug("Permissions", "Permission state unknown (settings is null)")
                    continuation.resume(PermissionState.Unknown)
                    return@getNotificationSettingsWithCompletionHandler
                }

                val isAuthorized = settings.authorizationStatus == UNAuthorizationStatusAuthorized
                logDebug("Permissions", "Permission state $isAuthorized")
                continuation.resume(when (isAuthorized) {
                    true -> PermissionState.Granted
                    false -> PermissionState.Denied
                })
            }
        }
    }
}