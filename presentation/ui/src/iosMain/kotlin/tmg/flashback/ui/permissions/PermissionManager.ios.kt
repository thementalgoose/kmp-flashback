package tmg.flashback.ui.permissions

import kotlinx.coroutines.CompletableDeferred
import platform.UserNotifications.UNAuthorizationOptionTimeSensitive
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.coroutines.suspendCoroutine

actual class PermissionManagerImpl actual constructor(): PermissionManager {

    actual override suspend fun requestPermission(permission: Permission): CompletableDeferred<PermissionState> {
        return when (permission) {
            Permission.Notifications -> suspendCoroutine { continuation ->
                UNUserNotificationCenter.currentNotificationCenter()
                    .requestAuthorizationWithOptions(UNAuthorizationOptionTimeSensitive) { success, error ->
                        if (success) {
                            continuation.resumeWith(Result.success(CompletableDeferred(PermissionState.Granted)))
                        } else if (error != null) {
                            continuation.resumeWith(Result.success(CompletableDeferred(PermissionState.NotGranted)))
                        } else {
                            continuation.resumeWith(Result.success(CompletableDeferred(PermissionState.NotDetermined)))
                        }
                    }
            }
        }
    }

    actual override suspend fun getPermissionState(permission: Permission): PermissionState {
        return when (permission) {
            Permission.Notifications -> suspendCoroutine { continuation ->
                UNUserNotificationCenter.currentNotificationCenter().getNotificationSettingsWithCompletionHandler { settings ->
                    when {
                        settings?.authorizationStatus == UNAuthorizationStatusNotDetermined ->
                            continuation.resumeWith(Result.success(PermissionState.NotDetermined))
                        settings?.authorizationStatus == UNAuthorizationStatusDenied ->
                            continuation.resumeWith(Result.success(PermissionState.NotGranted))
                        settings?.authorizationStatus == UNAuthorizationStatusAuthorized ->
                            continuation.resumeWith(Result.success(PermissionState.Granted))
                        else ->
                            continuation.resumeWith(Result.success(PermissionState.NotDetermined))
                    }
                }
            }
        }
    }
}