package tmg.flashback.ui.permissions

import kotlinx.coroutines.CompletableDeferred

actual class PermissionManagerImpl actual constructor(): PermissionManager {

    actual override suspend fun requestPermission(permission: Permission): CompletableDeferred<PermissionState> {
        return CompletableDeferred(PermissionState.NotGranted)
    }

    actual override suspend fun getPermissionState(permission: Permission): PermissionState {
        return PermissionState.NotGranted
    }
}