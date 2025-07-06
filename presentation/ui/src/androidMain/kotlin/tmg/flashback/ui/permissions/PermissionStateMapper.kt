package tmg.flashback.ui.permissions

fun PermissionState.toMoko(): dev.icerock.moko.permissions.PermissionState {
    return when (this) {
        PermissionState.NotDetermined -> dev.icerock.moko.permissions.PermissionState.NotDetermined
        PermissionState.NotGranted -> dev.icerock.moko.permissions.PermissionState.NotGranted
        PermissionState.Granted -> dev.icerock.moko.permissions.PermissionState.Granted
        PermissionState.Denied -> dev.icerock.moko.permissions.PermissionState.Denied
        PermissionState.DeniedAlways -> dev.icerock.moko.permissions.PermissionState.DeniedAlways
    }
}

fun dev.icerock.moko.permissions.PermissionState.fromMoko(): PermissionState {
    return when (this) {
        dev.icerock.moko.permissions.PermissionState.NotDetermined -> PermissionState.NotDetermined
        dev.icerock.moko.permissions.PermissionState.NotGranted -> PermissionState.NotGranted
        dev.icerock.moko.permissions.PermissionState.Granted -> PermissionState.Granted
        dev.icerock.moko.permissions.PermissionState.Denied -> PermissionState.Denied
        dev.icerock.moko.permissions.PermissionState.DeniedAlways -> PermissionState.DeniedAlways
    }
}