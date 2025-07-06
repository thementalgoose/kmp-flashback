package tmg.flashback.ui.permissions

import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION

fun Permission.toMoko(): dev.icerock.moko.permissions.Permission {
    return when (this) {
        Permission.Notifications -> dev.icerock.moko.permissions.Permission.REMOTE_NOTIFICATION
    }
}

fun dev.icerock.moko.permissions.Permission.fromMoko(): Permission {
    return when (this) {
        dev.icerock.moko.permissions.Permission.REMOTE_NOTIFICATION -> Permission.Notifications
        else -> throw NotImplementedError("Unsupported permission type")
    }
}