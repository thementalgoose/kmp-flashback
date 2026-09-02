package tmg.flashback.ui.permissions

import kotlinx.coroutines.CompletableDeferred

internal actual class PermissionManagerImpl actual constructor() : PermissionManager {
    actual override suspend fun requestPermission(permission: Permission) =
        CompletableDeferred(PermissionState.NotGranted)

    actual override suspend fun getPermissionState(permission: Permission) =
        PermissionState.NotGranted
}
